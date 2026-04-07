/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.ptr.PointerByReference
 *  org.slf4j.Logger
 *  tomp2p.opuswrapper.Opus
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.AudioNatives;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.AudioReceiveHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.AudioSendHandler;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.CombinedAudio;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.OpusPacket;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.SpeakingMode;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.UserAudio;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IAudioSendFactory;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IAudioSendSystem;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.factory.IPacketProvider;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.ExceptionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.AudioPacket;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.AudioWebSocket;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.Decoder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AudioManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TIntLongMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.TIntObjectMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TIntLongHashMap;
import com.hypherionmc.sdlink.shaded.gnu.trove.map.hash.TIntObjectHashMap;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocket;
import com.sun.jna.ptr.PointerByReference;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import tomp2p.opuswrapper.Opus;

public class AudioConnection {
    public static final Logger LOG = JDALogger.getLog(AudioConnection.class);
    public static final long MAX_UINT_32 = 0xFFFFFFFFL;
    private static final ByteBuffer silenceBytes = ByteBuffer.wrap(new byte[]{-8, -1, -2});
    private static boolean printedError = false;
    protected volatile DatagramSocket udpSocket;
    private final TIntLongMap ssrcMap = new TIntLongHashMap();
    private final TIntObjectMap<Decoder> opusDecoders = new TIntObjectHashMap<Decoder>();
    private final HashMap<User, Queue<AudioData>> combinedQueue = new HashMap();
    private final String threadIdentifier;
    private final AudioWebSocket webSocket;
    private final JDAImpl api;
    protected final ReentrantLock readyLock = new ReentrantLock();
    protected final Condition readyCondvar = this.readyLock.newCondition();
    private AudioChannel channel;
    private PointerByReference opusEncoder;
    private ScheduledExecutorService combinedAudioExecutor;
    private IAudioSendSystem sendSystem;
    private Thread receiveThread;
    private long queueTimeout;
    private boolean shutdown = false;
    private volatile AudioSendHandler sendHandler = null;
    private volatile AudioReceiveHandler receiveHandler = null;
    private volatile boolean couldReceive = false;
    private volatile int speakingMode = SpeakingMode.VOICE.getRaw();

    public AudioConnection(AudioManagerImpl manager, String endpoint, String sessionId, String token, AudioChannel channel) {
        this.api = (JDAImpl)channel.getJDA();
        this.channel = channel;
        JDAImpl api = (JDAImpl)channel.getJDA();
        this.threadIdentifier = api.getIdentifierString() + " AudioConnection Guild: " + channel.getGuild().getId();
        this.webSocket = new AudioWebSocket(this, manager.getListenerProxy(), endpoint, channel.getGuild(), sessionId, token, manager.isAutoReconnect());
    }

    public void startConnection() {
        this.webSocket.startConnection();
    }

    public ConnectionStatus getConnectionStatus() {
        return this.webSocket.getConnectionStatus();
    }

    public void setAutoReconnect(boolean shouldReconnect) {
        this.webSocket.setAutoReconnect(shouldReconnect);
    }

    @Deprecated
    public void setSpeakingDelay(int millis) {
    }

    public void setSendingHandler(AudioSendHandler handler) {
        this.sendHandler = handler;
        if (this.webSocket.isReady()) {
            this.setupSendSystem();
        }
    }

    public void setReceivingHandler(AudioReceiveHandler handler) {
        this.receiveHandler = handler;
        if (this.webSocket.isReady()) {
            this.setupReceiveSystem();
        }
    }

    public void setSpeakingMode(EnumSet<SpeakingMode> mode) {
        int raw = SpeakingMode.getRaw(mode);
        if (raw != this.speakingMode && this.webSocket.isReady()) {
            this.setSpeaking(raw);
        }
        this.speakingMode = raw;
    }

    public void setQueueTimeout(long queueTimeout) {
        this.queueTimeout = queueTimeout;
    }

    public AudioChannel getChannel() {
        return this.channel;
    }

    public void setChannel(AudioChannel channel) {
        this.channel = channel;
    }

    public JDAImpl getJDA() {
        return this.api;
    }

    public Guild getGuild() {
        return this.getChannel().getGuild();
    }

    public void close(ConnectionStatus closeStatus) {
        this.shutdown();
        this.webSocket.close(closeStatus);
    }

    public synchronized void shutdown() {
        this.shutdown = true;
        if (this.sendSystem != null) {
            this.sendSystem.shutdown();
            this.sendSystem = null;
        }
        if (this.receiveThread != null) {
            this.receiveThread.interrupt();
            this.receiveThread = null;
        }
        if (this.combinedAudioExecutor != null) {
            this.combinedAudioExecutor.shutdownNow();
            this.combinedAudioExecutor = null;
        }
        if (this.opusEncoder != null) {
            Opus.INSTANCE.opus_encoder_destroy(this.opusEncoder);
            this.opusEncoder = null;
        }
        this.opusDecoders.valueCollection().forEach(Decoder::close);
        this.opusDecoders.clear();
        MiscUtil.locked(this.readyLock, this.readyCondvar::signalAll);
    }

    public WebSocket getWebSocket() {
        return this.webSocket.socket;
    }

    protected void prepareReady() {
        Thread readyThread = new Thread(() -> {
            this.getJDA().setContext();
            boolean ready = MiscUtil.locked(this.readyLock, () -> {
                long timeout2 = this.getGuild().getAudioManager().getConnectTimeout();
                while (!this.webSocket.isReady()) {
                    try {
                        boolean activated = this.readyCondvar.await(timeout2, TimeUnit.MILLISECONDS);
                        if (!activated) {
                            this.webSocket.close(ConnectionStatus.ERROR_CONNECTION_TIMEOUT);
                            this.shutdown = true;
                        }
                        if (!this.shutdown) continue;
                        return false;
                    }
                    catch (InterruptedException e) {
                        LOG.error("AudioConnection ready thread got interrupted while sleeping", (Throwable)e);
                        return false;
                    }
                }
                return true;
            });
            if (ready) {
                this.setupSendSystem();
                this.setupReceiveSystem();
            }
        });
        readyThread.setUncaughtExceptionHandler((thread, throwable) -> {
            LOG.error("Uncaught exception in Audio ready-thread", throwable);
            JDAImpl api = this.getJDA();
            api.handleEvent(new ExceptionEvent(api, throwable, true));
        });
        readyThread.setDaemon(true);
        readyThread.setName(this.threadIdentifier + " Ready Thread");
        readyThread.start();
    }

    protected void removeUserSSRC(long userId) {
        AtomicInteger ssrcRef = new AtomicInteger(0);
        boolean modified = this.ssrcMap.retainEntries((ssrc, id) -> {
            boolean isEntry;
            boolean bl = isEntry = id == userId;
            if (isEntry) {
                ssrcRef.set(ssrc);
            }
            return !isEntry;
        });
        if (!modified) {
            return;
        }
        Decoder decoder = this.opusDecoders.remove(ssrcRef.get());
        if (decoder != null) {
            decoder.close();
        }
    }

    protected void updateUserSSRC(int ssrc, long userId) {
        if (this.ssrcMap.containsKey(ssrc)) {
            long previousId = this.ssrcMap.get(ssrc);
            if (previousId != userId) {
                LOG.error("Yeah.. So.. JDA received a UserSSRC update for an ssrc that already had a User set. Inform devs.\nChannelId: {} SSRC: {} oldId: {} newId: {}", new Object[]{this.channel.getId(), ssrc, previousId, userId});
            }
        } else {
            this.ssrcMap.put(ssrc, userId);
            if (this.receiveThread != null && AudioNatives.ensureOpus()) {
                this.opusDecoders.put(ssrc, new Decoder(ssrc));
            }
        }
    }

    private synchronized void setupSendSystem() {
        if (this.udpSocket != null && !this.udpSocket.isClosed() && this.sendHandler != null && this.sendSystem == null) {
            this.setSpeaking(this.speakingMode);
            IAudioSendFactory factory2 = this.getJDA().getAudioSendFactory();
            this.sendSystem = factory2.createSendSystem(new PacketProvider());
            this.sendSystem.setContextMap(this.getJDA().getContextMap());
            this.sendSystem.start();
        } else if (this.sendHandler == null && this.sendSystem != null) {
            this.sendSystem.shutdown();
            this.sendSystem = null;
            if (this.opusEncoder != null) {
                Opus.INSTANCE.opus_encoder_destroy(this.opusEncoder);
                this.opusEncoder = null;
            }
        }
    }

    private synchronized void setupReceiveSystem() {
        if (this.udpSocket != null && !this.udpSocket.isClosed() && this.receiveHandler != null && this.receiveThread == null) {
            this.setupReceiveThread();
        } else if (this.receiveHandler == null && this.receiveThread != null) {
            this.receiveThread.interrupt();
            this.receiveThread = null;
            if (this.combinedAudioExecutor != null) {
                this.combinedAudioExecutor.shutdownNow();
                this.combinedAudioExecutor = null;
            }
            this.opusDecoders.valueCollection().forEach(Decoder::close);
            this.opusDecoders.clear();
        } else if (this.receiveHandler != null && !this.receiveHandler.canReceiveCombined() && this.combinedAudioExecutor != null) {
            this.combinedAudioExecutor.shutdownNow();
            this.combinedAudioExecutor = null;
        }
    }

    private synchronized void setupReceiveThread() {
        if (this.receiveThread == null) {
            this.receiveThread = new Thread(() -> {
                this.getJDA().setContext();
                try {
                    this.udpSocket.setSoTimeout(1000);
                }
                catch (SocketException e) {
                    LOG.error("Couldn't set SO_TIMEOUT for UDP socket", (Throwable)e);
                }
                while (!this.udpSocket.isClosed() && !Thread.currentThread().isInterrupted()) {
                    DatagramPacket receivedPacket = new DatagramPacket(new byte[1920], 1920);
                    try {
                        boolean canReceive;
                        this.udpSocket.receive(receivedPacket);
                        boolean shouldDecode = this.receiveHandler != null && (this.receiveHandler.canReceiveUser() || this.receiveHandler.canReceiveCombined());
                        boolean bl = canReceive = this.receiveHandler != null && (this.receiveHandler.canReceiveUser() || this.receiveHandler.canReceiveCombined() || this.receiveHandler.canReceiveEncoded());
                        if (canReceive && this.webSocket.getSecretKey() != null) {
                            this.couldReceive = true;
                            AudioPacket decryptedPacket = AudioPacket.decryptAudioPacket(this.webSocket.crypto, receivedPacket);
                            if (decryptedPacket == null) continue;
                            int ssrc = decryptedPacket.getSSRC();
                            long userId = this.ssrcMap.get(ssrc);
                            Decoder decoder = this.opusDecoders.get(ssrc);
                            if (userId == this.ssrcMap.getNoEntryValue()) {
                                ByteBuffer audio = decryptedPacket.getEncodedAudio();
                                if (audio.equals(silenceBytes)) continue;
                                LOG.debug("Received audio data with an unknown SSRC id. Ignoring");
                                continue;
                            }
                            if (decoder == null) {
                                if (AudioNatives.ensureOpus()) {
                                    decoder = new Decoder(ssrc);
                                    this.opusDecoders.put(ssrc, decoder);
                                } else if (!this.receiveHandler.canReceiveEncoded()) {
                                    LOG.error("Unable to decode audio due to missing opus binaries!");
                                    break;
                                }
                            }
                            OpusPacket opusPacket = new OpusPacket(decryptedPacket, userId, decoder);
                            if (this.receiveHandler.canReceiveEncoded()) {
                                this.receiveHandler.handleEncodedAudio(opusPacket);
                            }
                            if (!shouldDecode || !opusPacket.canDecode()) continue;
                            User user = this.getJDA().getUserById(userId);
                            if (user == null) {
                                LOG.warn("Received audio data with a known SSRC, but the userId associate with the SSRC is unknown to JDA!");
                                continue;
                            }
                            short[] decodedAudio = opusPacket.decode();
                            if (decodedAudio == null) continue;
                            if (this.receiveHandler.canReceiveUser()) {
                                this.receiveHandler.handleUserAudio(new UserAudio(user, decodedAudio));
                            }
                            if (!this.receiveHandler.canReceiveCombined() || !this.receiveHandler.includeUserInCombinedAudio(user)) continue;
                            Queue<AudioData> queue = this.combinedQueue.get(user);
                            if (queue == null) {
                                queue = new ConcurrentLinkedQueue<AudioData>();
                                this.combinedQueue.put(user, queue);
                            }
                            queue.add(new AudioData(decodedAudio));
                            continue;
                        }
                        this.couldReceive = false;
                    }
                    catch (SocketTimeoutException shouldDecode) {
                    }
                    catch (SocketException shouldDecode) {
                    }
                    catch (Exception e) {
                        LOG.error("There was some random exception while waiting for udp packets", (Throwable)e);
                    }
                }
            });
            this.receiveThread.setUncaughtExceptionHandler((thread, throwable) -> {
                LOG.error("There was some uncaught exception in the audio receive thread", throwable);
                JDAImpl api = this.getJDA();
                api.handleEvent(new ExceptionEvent(api, throwable, true));
            });
            this.receiveThread.setDaemon(true);
            this.receiveThread.setName(this.threadIdentifier + " Receiving Thread");
            this.receiveThread.start();
        }
        if (this.receiveHandler.canReceiveCombined()) {
            this.setupCombinedExecutor();
        }
    }

    private synchronized void setupCombinedExecutor() {
        if (this.combinedAudioExecutor == null) {
            this.combinedAudioExecutor = Executors.newSingleThreadScheduledExecutor(task -> {
                Thread t = new Thread(task, this.threadIdentifier + " Combined Thread");
                t.setDaemon(true);
                t.setUncaughtExceptionHandler((thread, throwable) -> {
                    LOG.error("I have no idea how, but there was an uncaught exception in the combinedAudioExecutor", throwable);
                    JDAImpl api = this.getJDA();
                    api.handleEvent(new ExceptionEvent(api, throwable, true));
                });
                return t;
            });
            this.combinedAudioExecutor.scheduleAtFixedRate(() -> {
                this.getJDA().setContext();
                try {
                    LinkedList<User> users = new LinkedList<User>();
                    LinkedList<short[]> audioParts = new LinkedList<short[]>();
                    if (this.receiveHandler != null && this.receiveHandler.canReceiveCombined()) {
                        long currentTime = System.currentTimeMillis();
                        for (Map.Entry<User, Queue<AudioData>> entry : this.combinedQueue.entrySet()) {
                            User user = entry.getKey();
                            Queue<AudioData> queue = entry.getValue();
                            if (queue.isEmpty()) continue;
                            AudioData audioData = queue.poll();
                            while (audioData != null && currentTime - audioData.time > this.queueTimeout) {
                                audioData = queue.poll();
                            }
                            if (audioData == null) continue;
                            users.add(user);
                            audioParts.add(audioData.data);
                        }
                        if (!audioParts.isEmpty()) {
                            int audioLength = audioParts.stream().mapToInt(it -> ((short[])it).length).max().getAsInt();
                            short[] mix = new short[1920];
                            for (int i = 0; i < audioLength; ++i) {
                                int sample = 0;
                                Iterator iterator2 = audioParts.iterator();
                                while (iterator2.hasNext()) {
                                    short[] audio = (short[])iterator2.next();
                                    if (i < audio.length) {
                                        sample += audio[i];
                                        continue;
                                    }
                                    iterator2.remove();
                                }
                                mix[i] = sample > Short.MAX_VALUE ? Short.MAX_VALUE : (sample < Short.MIN_VALUE ? Short.MIN_VALUE : (short)sample);
                            }
                            this.receiveHandler.handleCombinedAudio(new CombinedAudio(users, mix));
                        } else {
                            this.receiveHandler.handleCombinedAudio(new CombinedAudio(Collections.emptyList(), new short[1920]));
                        }
                    }
                }
                catch (Exception e) {
                    LOG.error("There was some unexpected exception in the combinedAudioExecutor!", (Throwable)e);
                }
            }, 0L, 20L, TimeUnit.MILLISECONDS);
        }
    }

    private ByteBuffer encodeToOpus(ByteBuffer rawAudio) {
        ShortBuffer nonEncodedBuffer = ShortBuffer.allocate(rawAudio.remaining() / 2);
        ByteBuffer encoded = ByteBuffer.allocate(4096);
        for (int i = rawAudio.position(); i < rawAudio.limit(); i += 2) {
            int firstByte = 0xFF & rawAudio.get(i);
            int secondByte = 0xFF & rawAudio.get(i + 1);
            short toShort = (short)(firstByte << 8 | secondByte);
            nonEncodedBuffer.put(toShort);
        }
        ((Buffer)nonEncodedBuffer).flip();
        int result = Opus.INSTANCE.opus_encode(this.opusEncoder, nonEncodedBuffer, 960, encoded, encoded.capacity());
        if (result <= 0) {
            LOG.error("Received error code from opus_encode(...): {}", (Object)result);
            return null;
        }
        ((Buffer)encoded).position(0).limit(result);
        return encoded;
    }

    private void setSpeaking(int raw) {
        DataObject obj = DataObject.empty().put("speaking", raw).put("ssrc", this.webSocket.getSSRC()).put("delay", 0);
        this.webSocket.send(5, obj);
    }

    protected void finalize() {
        this.shutdown();
    }

    private class PacketProvider
    implements IPacketProvider {
        private char seq = '\u0000';
        private int timestamp = 0;
        private ByteBuffer buffer = ByteBuffer.allocate(512);
        private ByteBuffer encryptionBuffer = ByteBuffer.allocate(512);

        private PacketProvider() {
        }

        @Override
        @Nonnull
        public String getIdentifier() {
            return AudioConnection.this.threadIdentifier;
        }

        @Override
        @Nonnull
        public AudioChannel getConnectedChannel() {
            return AudioConnection.this.getChannel();
        }

        @Override
        @Nonnull
        public DatagramSocket getUdpSocket() {
            return AudioConnection.this.udpSocket;
        }

        @Override
        @Nonnull
        public InetSocketAddress getSocketAddress() {
            return AudioConnection.this.webSocket.getAddress();
        }

        @Override
        public DatagramPacket getNextPacket(boolean unused) {
            ByteBuffer buffer = this.getNextPacketRaw(unused);
            return buffer == null ? null : this.getDatagramPacket(buffer);
        }

        @Override
        public ByteBuffer getNextPacketRaw(boolean unused) {
            ByteBuffer nextPacket = null;
            try {
                if (AudioConnection.this.sendHandler != null && AudioConnection.this.sendHandler.canProvide()) {
                    ByteBuffer rawAudio = AudioConnection.this.sendHandler.provide20MsAudio();
                    if (rawAudio != null && !rawAudio.hasArray()) {
                        LOG.error("AudioSendHandler provided ByteBuffer without a backing array! This is unsupported.");
                    }
                    if (rawAudio != null && rawAudio.hasRemaining() && rawAudio.hasArray()) {
                        if (!AudioConnection.this.sendHandler.isOpus() && (rawAudio = this.encodeAudio(rawAudio)) == null) {
                            return null;
                        }
                        nextPacket = this.getPacketData(rawAudio);
                        this.seq = this.seq + '\u0001' > 65535 ? (char)'\u0000' : (char)(this.seq + '\u0001');
                    }
                }
            }
            catch (Exception e) {
                LOG.error("There was an error while getting next audio packet", (Throwable)e);
            }
            if (nextPacket != null) {
                this.timestamp += 960;
            }
            return nextPacket;
        }

        private ByteBuffer encodeAudio(ByteBuffer rawAudio) {
            if (AudioConnection.this.opusEncoder == null) {
                if (!AudioNatives.ensureOpus()) {
                    if (!printedError) {
                        LOG.error("Unable to process PCM audio without opus binaries!");
                    }
                    printedError = true;
                    return null;
                }
                IntBuffer error = IntBuffer.allocate(1);
                AudioConnection.this.opusEncoder = Opus.INSTANCE.opus_encoder_create(48000, 2, 2049, error);
                if (error.get() != 0 && AudioConnection.this.opusEncoder == null) {
                    LOG.error("Received error status from opus_encoder_create(...): {}", (Object)error.get());
                    return null;
                }
            }
            return AudioConnection.this.encodeToOpus(rawAudio);
        }

        private DatagramPacket getDatagramPacket(ByteBuffer b) {
            byte[] data = b.array();
            int offset = b.arrayOffset() + b.position();
            int length = b.remaining();
            return new DatagramPacket(data, offset, length, AudioConnection.this.webSocket.getAddress());
        }

        private ByteBuffer getPacketData(ByteBuffer rawAudio) {
            this.ensureEncryptionBuffer(rawAudio);
            AudioPacket packet = new AudioPacket(this.encryptionBuffer, this.seq, this.timestamp, AudioConnection.this.webSocket.getSSRC(), rawAudio);
            this.buffer = packet.asEncryptedPacket(((AudioConnection)AudioConnection.this).webSocket.crypto, this.buffer);
            return this.buffer;
        }

        private void ensureEncryptionBuffer(ByteBuffer data) {
            ((Buffer)this.encryptionBuffer).clear();
            int currentCapacity = this.encryptionBuffer.remaining();
            int requiredCapacity = 12 + data.remaining();
            if (currentCapacity < requiredCapacity) {
                this.encryptionBuffer = ByteBuffer.allocate(requiredCapacity);
            }
        }

        @Override
        public void onConnectionError(@Nonnull ConnectionStatus status) {
            LOG.warn("IAudioSendSystem reported a connection error of: {}", (Object)status);
            LOG.warn("Shutting down AudioConnection.");
            AudioConnection.this.webSocket.close(status);
        }

        @Override
        public void onConnectionLost() {
            LOG.warn("Closing AudioConnection due to inability to send audio packets.");
            LOG.warn("Cannot send audio packet because JDA cannot navigate the route to Discord.\nAre you sure you have internet connection? It is likely that you've lost connection.");
            AudioConnection.this.webSocket.close(ConnectionStatus.ERROR_LOST_CONNECTION);
        }
    }

    private static class AudioData {
        private final long time = System.currentTimeMillis();
        private final short[] data;

        public AudioData(short[] data) {
            this.data = data;
        }
    }
}

