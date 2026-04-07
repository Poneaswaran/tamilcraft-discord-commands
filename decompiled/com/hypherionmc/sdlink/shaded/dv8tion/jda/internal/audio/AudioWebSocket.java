/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.SpeakingMode;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionListener;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Guild;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.User;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.UserSnowflake;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.events.ExceptionEvent;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.JDAImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.AudioConnection;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.AudioEncryption;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.CryptoAdapter;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.audio.VoiceCode;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.managers.AudioManagerImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.IOUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.ThreadType;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocket;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketAdapter;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketException;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFactory;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFrame;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.slf4j.Logger;

class AudioWebSocket
extends WebSocketAdapter {
    public static final Logger LOG = JDALogger.getLog(AudioWebSocket.class);
    public static final int DISCORD_SECRET_KEY_LENGTH = 32;
    private static final byte[] UDP_KEEP_ALIVE = new byte[]{-55, 0, 0, 0, 0, 0, 0, 0, 0};
    protected volatile AudioEncryption encryption;
    protected volatile CryptoAdapter crypto;
    protected WebSocket socket;
    private final AudioConnection audioConnection;
    private final ConnectionListener listener;
    private final ScheduledExecutorService keepAlivePool;
    private final Guild guild;
    private final String sessionId;
    private final String token;
    private final String wssEndpoint;
    private volatile ConnectionStatus connectionStatus = ConnectionStatus.NOT_CONNECTED;
    private boolean ready = false;
    private boolean reconnecting = false;
    private boolean shouldReconnect;
    private int ssrc;
    private byte[] secretKey;
    private Future<?> keepAliveHandle;
    private InetSocketAddress address;
    private long sequence;
    private volatile boolean shutdown = false;

    protected AudioWebSocket(AudioConnection audioConnection, ConnectionListener listener, String endpoint, Guild guild, String sessionId, String token, boolean shouldReconnect) {
        this.audioConnection = audioConnection;
        this.listener = listener;
        this.guild = guild;
        this.sessionId = sessionId;
        this.token = token;
        this.shouldReconnect = shouldReconnect;
        this.keepAlivePool = this.getJDA().getAudioLifeCyclePool();
        String url = IOUtil.addQuery(endpoint, "v", 8);
        this.wssEndpoint = url.startsWith("wss://") ? url : "wss://" + url;
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("Cannot create a audio websocket connection using a null/empty sessionId!");
        }
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Cannot create a audio websocket connection using a null/empty token!");
        }
    }

    protected void send(String message) {
        LOG.trace("<- {}", (Object)message);
        this.socket.sendText(message);
    }

    protected void send(int op, Object data) {
        this.send(DataObject.empty().put("op", op).put("d", data).toString());
    }

    protected void startConnection() {
        if (!this.reconnecting && this.socket != null) {
            throw new IllegalStateException("Somehow, someway, this AudioWebSocket has already attempted to start a connection!");
        }
        try {
            WebSocketFactory socketFactory = new WebSocketFactory(this.getJDA().getWebSocketFactory());
            IOUtil.setServerName(socketFactory, this.wssEndpoint);
            if (socketFactory.getSocketTimeout() > 0) {
                socketFactory.setSocketTimeout(Math.max(1000, socketFactory.getSocketTimeout()));
            } else {
                socketFactory.setSocketTimeout(10000);
            }
            this.socket = socketFactory.createSocket(this.wssEndpoint);
            this.socket.setDirectTextMessage(true);
            this.socket.addListener(this);
            this.changeStatus(ConnectionStatus.CONNECTING_AWAITING_WEBSOCKET_CONNECT);
            this.socket.connectAsynchronously();
        }
        catch (IOException e) {
            LOG.warn("Encountered IOException while attempting to connect to {}: {}\nClosing connection and attempting to reconnect.", (Object)this.wssEndpoint, (Object)e.getMessage());
            this.close(ConnectionStatus.ERROR_WEBSOCKET_UNABLE_TO_CONNECT);
        }
    }

    protected void close(ConnectionStatus closeStatus) {
        if (this.shutdown) {
            return;
        }
        this.locked(manager -> {
            AudioChannel channel;
            Guild connGuild;
            if (this.shutdown) {
                return;
            }
            ConnectionStatus status = closeStatus;
            this.ready = false;
            this.shutdown = true;
            this.stopKeepAlive();
            if (this.audioConnection.udpSocket != null) {
                this.audioConnection.udpSocket.close();
            }
            if (this.socket != null) {
                this.socket.sendClose();
            }
            this.audioConnection.shutdown();
            AudioChannelUnion disconnectedChannel = manager.getConnectedChannel();
            manager.setAudioConnection(null);
            JDAImpl api = this.getJDA();
            if (!(status != ConnectionStatus.DISCONNECTED_KICKED_FROM_CHANNEL || api.getClient().isSession() && api.getClient().isConnected())) {
                LOG.debug("Connection was closed due to session invalidate!");
                status = ConnectionStatus.ERROR_CANNOT_RESUME;
            } else if ((status == ConnectionStatus.ERROR_LOST_CONNECTION || status == ConnectionStatus.DISCONNECTED_KICKED_FROM_CHANNEL) && (connGuild = api.getGuildById(this.guild.getIdLong())) != null && (channel = (AudioChannel)connGuild.getGuildChannelById(this.audioConnection.getChannel().getIdLong())) == null) {
                status = ConnectionStatus.DISCONNECTED_CHANNEL_DELETED;
            }
            this.changeStatus(status);
            if (this.shouldReconnect && status.shouldReconnect() && status != ConnectionStatus.AUDIO_REGION_CHANGE) {
                if (disconnectedChannel == null) {
                    LOG.debug("Cannot reconnect due to null audio channel");
                    return;
                }
                api.getDirectAudioController().reconnect(disconnectedChannel);
            } else if (status == ConnectionStatus.DISCONNECTED_REMOVED_FROM_GUILD) {
                api.getAudioManagersView().remove(this.guild.getIdLong());
            } else if (status != ConnectionStatus.AUDIO_REGION_CHANGE && status != ConnectionStatus.DISCONNECTED_KICKED_FROM_CHANNEL) {
                api.getDirectAudioController().disconnect(this.guild);
            }
        });
    }

    protected void changeStatus(ConnectionStatus newStatus) {
        this.connectionStatus = newStatus;
        this.listener.onStatusChange(newStatus);
    }

    protected void setAutoReconnect(boolean shouldReconnect) {
        this.shouldReconnect = shouldReconnect;
    }

    protected ConnectionStatus getConnectionStatus() {
        return this.connectionStatus;
    }

    protected InetSocketAddress getAddress() {
        return this.address;
    }

    protected byte[] getSecretKey() {
        return this.secretKey;
    }

    protected int getSSRC() {
        return this.ssrc;
    }

    protected boolean isReady() {
        return this.ready;
    }

    @Override
    public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) {
        this.getJDA().setContext();
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
        if (this.shutdown) {
            this.socket.sendClose(1000);
            return;
        }
        if (this.reconnecting) {
            this.resume();
        } else {
            this.identify();
        }
        this.changeStatus(ConnectionStatus.CONNECTING_AWAITING_AUTHENTICATION);
        this.audioConnection.prepareReady();
        this.reconnecting = false;
    }

    @Override
    public void onTextMessage(WebSocket websocket, byte[] data) {
        try {
            this.handleEvent(DataObject.fromJson(data));
        }
        catch (Exception ex) {
            String message = "malformed";
            try {
                message = new String(data, StandardCharsets.UTF_8);
            }
            catch (Exception exception) {
                // empty catch block
            }
            LOG.error("Encountered exception trying to handle an event message: {}", (Object)message, (Object)ex);
        }
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        if (this.shutdown) {
            return;
        }
        LOG.debug("The Audio connection was closed!\nBy remote? {}", (Object)closedByServer);
        if (serverCloseFrame != null) {
            LOG.debug("Reason: {}\nClose code: {}", (Object)serverCloseFrame.getCloseReason(), (Object)serverCloseFrame.getCloseCode());
            int code = serverCloseFrame.getCloseCode();
            VoiceCode.Close closeCode = VoiceCode.Close.from(code);
            switch (closeCode) {
                case SERVER_NOT_FOUND: 
                case SERVER_CRASH: 
                case INVALID_SESSION: {
                    this.close(ConnectionStatus.ERROR_CANNOT_RESUME);
                    break;
                }
                case AUTHENTICATION_FAILED: {
                    this.close(ConnectionStatus.DISCONNECTED_AUTHENTICATION_FAILURE);
                    break;
                }
                case DISCONNECTED: {
                    this.close(ConnectionStatus.DISCONNECTED_KICKED_FROM_CHANNEL);
                    break;
                }
                default: {
                    this.reconnect();
                }
            }
            return;
        }
        if (clientCloseFrame != null) {
            LOG.debug("ClientReason: {}\nClientCode: {}", (Object)clientCloseFrame.getCloseReason(), (Object)clientCloseFrame.getCloseCode());
            if (clientCloseFrame.getCloseCode() != 1000) {
                this.reconnect();
                return;
            }
        }
        this.close(ConnectionStatus.NOT_CONNECTED);
    }

    @Override
    public void onUnexpectedError(WebSocket websocket, WebSocketException cause) {
        this.handleCallbackError(websocket, cause);
    }

    @Override
    public void handleCallbackError(WebSocket websocket, Throwable cause) {
        LOG.error("There was some audio websocket error", cause);
        JDAImpl api = this.getJDA();
        api.handleEvent(new ExceptionEvent(api, cause, true));
    }

    @Override
    public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) {
        String identifier = this.getJDA().getIdentifierString();
        String guildId = this.guild.getId();
        switch (threadType) {
            case CONNECT_THREAD: {
                thread.setName(identifier + " AudioWS-ConnectThread (guildId: " + guildId + ')');
                break;
            }
            case FINISH_THREAD: {
                thread.setName(identifier + " AudioWS-FinishThread (guildId: " + guildId + ')');
                break;
            }
            case WRITING_THREAD: {
                thread.setName(identifier + " AudioWS-WriteThread (guildId: " + guildId + ')');
                break;
            }
            case READING_THREAD: {
                thread.setName(identifier + " AudioWS-ReadThread (guildId: " + guildId + ')');
                break;
            }
            default: {
                thread.setName(identifier + " AudioWS-" + (Object)((Object)threadType) + " (guildId: " + guildId + ')');
            }
        }
    }

    @Override
    public void onConnectError(WebSocket webSocket, WebSocketException e) {
        LOG.warn("Failed to establish websocket connection to {}: {} - {}\nClosing connection and attempting to reconnect.", new Object[]{this.wssEndpoint, e.getError(), e.getMessage()});
        this.close(ConnectionStatus.ERROR_WEBSOCKET_UNABLE_TO_CONNECT);
    }

    private void handleEvent(DataObject contentAll) {
        int opCode = contentAll.getInt("op");
        this.sequence = contentAll.getLong("seq", this.sequence);
        switch (opCode) {
            case 8: {
                LOG.trace("-> HELLO {}", (Object)contentAll);
                DataObject payload = contentAll.getObject("d");
                int interval = payload.getInt("heartbeat_interval");
                this.stopKeepAlive();
                this.setupKeepAlive(interval);
                break;
            }
            case 2: {
                InetSocketAddress externalIpAndPort;
                LOG.trace("-> READY {}", (Object)contentAll);
                DataObject content = contentAll.getObject("d");
                this.ssrc = content.getInt("ssrc");
                int port = content.getInt("port");
                String ip = content.getString("ip");
                DataArray modes = content.getArray("modes");
                this.encryption = CryptoAdapter.negotiate(AudioEncryption.fromArray(modes));
                if (this.encryption == null) {
                    this.close(ConnectionStatus.ERROR_UNSUPPORTED_ENCRYPTION_MODES);
                    LOG.error("None of the provided encryption modes are supported: {}", (Object)modes);
                    return;
                }
                LOG.debug("Using encryption mode " + this.encryption.getKey());
                this.changeStatus(ConnectionStatus.CONNECTING_ATTEMPTING_UDP_DISCOVERY);
                int tries = 0;
                do {
                    if ((externalIpAndPort = this.handleUdpDiscovery(new InetSocketAddress(ip, port), this.ssrc)) != null || ++tries <= 5) continue;
                    this.close(ConnectionStatus.ERROR_UDP_UNABLE_TO_CONNECT);
                    return;
                } while (externalIpAndPort == null);
                DataObject object = DataObject.empty().put("protocol", "udp").put("data", DataObject.empty().put("address", externalIpAndPort.getHostString()).put("port", externalIpAndPort.getPort()).put("mode", this.encryption.getKey()));
                this.send(1, object);
                this.changeStatus(ConnectionStatus.CONNECTING_AWAITING_READY);
                break;
            }
            case 9: {
                LOG.trace("-> RESUMED {}", (Object)contentAll);
                LOG.debug("Successfully resumed session!");
                this.changeStatus(ConnectionStatus.CONNECTED);
                this.ready = true;
                MiscUtil.locked(this.audioConnection.readyLock, this.audioConnection.readyCondvar::signalAll);
                break;
            }
            case 4: {
                LOG.trace("-> SESSION_DESCRIPTION {}", (Object)contentAll);
                this.send(5, DataObject.empty().put("delay", 0).put("speaking", 0).put("ssrc", this.ssrc));
                DataArray keyArray = contentAll.getObject("d").getArray("secret_key");
                this.secretKey = new byte[32];
                for (int i = 0; i < keyArray.length(); ++i) {
                    this.secretKey[i] = (byte)keyArray.getInt(i);
                }
                this.crypto = CryptoAdapter.getAdapter(this.encryption, this.secretKey);
                LOG.debug("Audio connection has finished connecting!");
                this.ready = true;
                MiscUtil.locked(this.audioConnection.readyLock, this.audioConnection.readyCondvar::signalAll);
                this.changeStatus(ConnectionStatus.CONNECTED);
                break;
            }
            case 3: {
                LOG.trace("-> HEARTBEAT {}", (Object)contentAll);
                this.send(3, System.currentTimeMillis());
                break;
            }
            case 6: {
                LOG.trace("-> HEARTBEAT_ACK {}", (Object)contentAll);
                long ping = System.currentTimeMillis() - contentAll.getObject("d").getLong("t");
                this.listener.onPing(ping);
                break;
            }
            case 5: {
                LOG.trace("-> USER_SPEAKING_UPDATE {}", (Object)contentAll);
                DataObject content = contentAll.getObject("d");
                EnumSet<SpeakingMode> speaking = SpeakingMode.getModes(content.getInt("speaking"));
                int ssrc = content.getInt("ssrc");
                long userId = content.getLong("user_id");
                User user = this.getUser(userId);
                if (user == null) {
                    LOG.trace("Got an Audio USER_SPEAKING_UPDATE for a non-existent User. JSON: {}", (Object)contentAll);
                    this.listener.onUserSpeakingModeUpdate(UserSnowflake.fromId(userId), speaking);
                } else {
                    this.listener.onUserSpeakingModeUpdate((UserSnowflake)user, speaking);
                }
                this.audioConnection.updateUserSSRC(ssrc, userId);
                break;
            }
            case 13: {
                LOG.trace("-> USER_DISCONNECT {}", (Object)contentAll);
                DataObject payload = contentAll.getObject("d");
                long userId = payload.getLong("user_id");
                this.audioConnection.removeUserSSRC(userId);
                break;
            }
            case 12: 
            case 14: {
                LOG.trace("-> OP {} {}", (Object)opCode, (Object)contentAll);
                break;
            }
            default: {
                LOG.debug("Unknown Audio OP code.\n{}", (Object)contentAll);
            }
        }
    }

    private void identify() {
        this.sequence = 0L;
        DataObject connectObj = DataObject.empty().put("server_id", this.guild.getId()).put("user_id", this.getJDA().getSelfUser().getId()).put("session_id", this.sessionId).put("token", this.token);
        this.send(0, connectObj);
    }

    private void resume() {
        LOG.debug("Sending resume payload...");
        DataObject resumeObj = DataObject.empty().put("server_id", this.guild.getId()).put("session_id", this.sessionId).put("token", this.token).put("seq_ack", this.sequence);
        this.send(7, resumeObj);
    }

    private JDAImpl getJDA() {
        return this.audioConnection.getJDA();
    }

    private void locked(Consumer<AudioManagerImpl> consumer) {
        AudioManagerImpl manager = (AudioManagerImpl)this.guild.getAudioManager();
        MiscUtil.locked(manager.CONNECTION_LOCK, () -> consumer.accept(manager));
    }

    private void reconnect() {
        if (this.shutdown) {
            return;
        }
        this.locked(unused -> {
            if (this.shutdown) {
                return;
            }
            this.ready = false;
            this.reconnecting = true;
            this.changeStatus(ConnectionStatus.ERROR_LOST_CONNECTION);
            this.startConnection();
        });
    }

    private InetSocketAddress handleUdpDiscovery(InetSocketAddress address, int ssrc) {
        try {
            if (this.audioConnection.udpSocket != null) {
                this.audioConnection.udpSocket.close();
            }
            this.audioConnection.udpSocket = new DatagramSocket();
            ByteBuffer buffer = ByteBuffer.allocate(74);
            buffer.putShort((short)1);
            buffer.putShort((short)70);
            buffer.putInt(ssrc);
            DatagramPacket discoveryPacket = new DatagramPacket(buffer.array(), buffer.array().length, address);
            this.audioConnection.udpSocket.send(discoveryPacket);
            DatagramPacket receivedPacket = new DatagramPacket(new byte[74], 74);
            this.audioConnection.udpSocket.setSoTimeout(1000);
            this.audioConnection.udpSocket.receive(receivedPacket);
            byte[] received = receivedPacket.getData();
            String ourIP = new String(received, 8, received.length - 10);
            ourIP = ourIP.trim();
            int ourPort = IOUtil.getShortBigEndian(received, received.length - 2) & 0xFFFF;
            this.address = address;
            return new InetSocketAddress(ourIP, ourPort);
        }
        catch (IOException e) {
            return null;
        }
    }

    private void stopKeepAlive() {
        if (this.keepAliveHandle != null) {
            this.keepAliveHandle.cancel(true);
        }
        this.keepAliveHandle = null;
    }

    private void setupKeepAlive(int keepAliveInterval) {
        if (this.keepAliveHandle != null) {
            LOG.error("Setting up a KeepAlive runnable while the previous one seems to still be active!!");
        }
        try {
            Socket rawSocket;
            if (this.socket != null && (rawSocket = this.socket.getSocket()) != null) {
                rawSocket.setSoTimeout(keepAliveInterval + 10000);
            }
        }
        catch (SocketException ex) {
            LOG.warn("Failed to setup timeout for socket", (Throwable)ex);
        }
        Runnable keepAliveRunnable = () -> {
            this.getJDA().setContext();
            if (this.socket != null && this.socket.isOpen()) {
                DataObject packet = DataObject.empty().put("t", System.currentTimeMillis());
                if (this.sequence > 0L) {
                    packet.put("seq_ack", this.sequence);
                }
                this.send(3, packet);
            }
            if (this.audioConnection.udpSocket != null && !this.audioConnection.udpSocket.isClosed()) {
                try {
                    DatagramPacket keepAlivePacket = new DatagramPacket(UDP_KEEP_ALIVE, UDP_KEEP_ALIVE.length, this.address);
                    this.audioConnection.udpSocket.send(keepAlivePacket);
                }
                catch (NoRouteToHostException e) {
                    LOG.warn("Closing AudioConnection due to inability to ping audio packets.");
                    LOG.warn("Cannot send audio packet because JDA navigate the route to Discord.\nAre you sure you have internet connection? It is likely that you've lost connection.");
                    this.close(ConnectionStatus.ERROR_LOST_CONNECTION);
                }
                catch (IOException e) {
                    LOG.error("There was some error sending an audio keepalive packet", (Throwable)e);
                }
            }
        };
        try {
            this.keepAliveHandle = this.keepAlivePool.scheduleAtFixedRate(keepAliveRunnable, 0L, keepAliveInterval, TimeUnit.MILLISECONDS);
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            // empty catch block
        }
    }

    private User getUser(long userId) {
        return this.getJDA().getUserById(userId);
    }

    protected void finalize() {
        if (!this.shutdown) {
            LOG.error("Finalization hook of AudioWebSocket was triggered without properly shutting down");
            this.close(ConnectionStatus.NOT_CONNECTED);
        }
    }
}

