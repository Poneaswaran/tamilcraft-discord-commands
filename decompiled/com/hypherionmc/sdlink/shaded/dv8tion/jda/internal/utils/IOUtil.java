/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.JDALogger;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.requestbody.BufferedRequestBody;
import com.hypherionmc.sdlink.shaded.neovisionaries.ws.client.WebSocketFactory;
import com.hypherionmc.sdlink.shaded.okhttp3.ConnectionPool;
import com.hypherionmc.sdlink.shaded.okhttp3.Dispatcher;
import com.hypherionmc.sdlink.shaded.okhttp3.MediaType;
import com.hypherionmc.sdlink.shaded.okhttp3.OkHttpClient;
import com.hypherionmc.sdlink.shaded.okhttp3.Response;
import com.hypherionmc.sdlink.shaded.okio.Okio;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;
import org.slf4j.Logger;

public class IOUtil {
    private static final Logger log = JDALogger.getLog(IOUtil.class);

    public static void silentClose(AutoCloseable closeable) {
        try {
            closeable.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void silentClose(Closeable closeable) {
        try {
            closeable.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static String addQuery(String base, Object ... params) {
        try {
            StringBuilder builder = new StringBuilder(base);
            if (new URI(base).getQuery() == null) {
                builder.append('?');
            } else {
                builder.append('&');
            }
            for (int i = 0; i < params.length; i += 2) {
                builder.append(params[i]).append('=').append(URLEncoder.encode(params[i + 1].toString(), "UTF-8")).append('&');
            }
            builder.setLength(builder.length() - 1);
            return builder.toString();
        }
        catch (UnsupportedEncodingException | URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getHost(String uri) {
        return URI.create(uri).getHost();
    }

    public static void setServerName(WebSocketFactory factory2, String url) {
        String host = IOUtil.getHost(url);
        if (host != null) {
            factory2.setServerName(host);
        }
    }

    public static OkHttpClient.Builder newHttpClientBuilder() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(25);
        ConnectionPool connectionPool = new ConnectionPool(5, 10L, TimeUnit.SECONDS);
        return new OkHttpClient.Builder().connectionPool(connectionPool).dispatcher(dispatcher);
    }

    public static byte[] readFully(File file) throws IOException {
        Checks.notNull(file, "File");
        Checks.check(file.exists(), "Provided file does not exist!");
        try (FileInputStream is = new FileInputStream(file);){
            int offset;
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                throw new IOException("Cannot read the file into memory completely due to it being too large!");
            }
            byte[] bytes = new byte[(int)length];
            int numRead = 0;
            for (offset = 0; offset < bytes.length && (numRead = ((InputStream)is).read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead) {
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
            ((InputStream)is).close();
            byte[] byArray = bytes;
            return byArray;
        }
    }

    public static byte[] readFully(InputStream stream) throws IOException {
        Checks.notNull(stream, "InputStream");
        byte[] buffer = new byte[1024];
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();){
            int readAmount = 0;
            while ((readAmount = stream.read(buffer)) != -1) {
                bos.write(buffer, 0, readAmount);
            }
            byte[] byArray = bos.toByteArray();
            return byArray;
        }
    }

    public static BufferedRequestBody createRequestBody(MediaType contentType, InputStream stream) {
        return new BufferedRequestBody(Okio.source(stream), contentType);
    }

    public static short getShortBigEndian(byte[] arr, int offset) {
        return (short)((arr[offset] & 0xFF) << 8 | arr[offset + 1] & 0xFF);
    }

    public static short getShortLittleEndian(byte[] arr, int offset) {
        return (short)(arr[offset] & 0xFF | (arr[offset + 1] & 0xFF) << 8);
    }

    public static int getIntBigEndian(byte[] arr, int offset) {
        return arr[offset + 3] & 0xFF | (arr[offset + 2] & 0xFF) << 8 | (arr[offset + 1] & 0xFF) << 16 | (arr[offset] & 0xFF) << 24;
    }

    public static void setIntBigEndian(byte[] arr, int offset, int it) {
        arr[offset] = (byte)(it >>> 24 & 0xFF);
        arr[offset + 1] = (byte)(it >>> 16 & 0xFF);
        arr[offset + 2] = (byte)(it >>> 8 & 0xFF);
        arr[offset + 3] = (byte)(it & 0xFF);
    }

    public static ByteBuffer reallocate(ByteBuffer original, int length) {
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.put(original);
        return buffer;
    }

    public static InputStream getBody(Response response) throws IOException {
        String encoding = response.header("content-encoding", "");
        BufferedInputStream data = new BufferedInputStream(response.body().byteStream());
        ((InputStream)data).mark(256);
        try {
            if (encoding.equalsIgnoreCase("gzip")) {
                return new GZIPInputStream(data);
            }
            if (encoding.equalsIgnoreCase("deflate")) {
                return new InflaterInputStream(data, new Inflater(true));
            }
        }
        catch (EOFException | ZipException ex) {
            ((InputStream)data).reset();
            log.error("Failed to read gzip content for response. Headers: {}\nContent: '{}'", new Object[]{response.headers(), JDALogger.getLazyString(() -> new String(IOUtil.readFully(data))), ex});
            return null;
        }
        return data;
    }
}

