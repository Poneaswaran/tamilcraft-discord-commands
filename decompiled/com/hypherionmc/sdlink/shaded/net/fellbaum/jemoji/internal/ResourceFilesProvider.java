/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.net.fellbaum.jemoji.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public interface ResourceFilesProvider {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    default public Object readFileAsObject(String filePathName) {
        try {
            try (InputStream is = this.getClass().getResourceAsStream(filePathName);){
                if (null == is) {
                    throw new IllegalStateException("InputStream is null");
                }
                ObjectInputStream ois = new ObjectInputStream(is);
                Object readObject = ois.readObject();
                ois.close();
                Object object = readObject;
                return object;
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

