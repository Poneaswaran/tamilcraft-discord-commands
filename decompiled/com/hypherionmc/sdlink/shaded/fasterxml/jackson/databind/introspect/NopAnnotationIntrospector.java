/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.Version;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.cfg.PackageVersion;
import java.io.Serializable;

public abstract class NopAnnotationIntrospector
extends AnnotationIntrospector
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final NopAnnotationIntrospector instance = new NopAnnotationIntrospector(){
        private static final long serialVersionUID = 1L;

        @Override
        public Version version() {
            return PackageVersion.VERSION;
        }
    };

    @Override
    public Version version() {
        return Version.unknownVersion();
    }
}

