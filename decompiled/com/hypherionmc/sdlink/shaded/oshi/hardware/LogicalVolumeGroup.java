/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.Immutable;
import java.util.Map;
import java.util.Set;

@Immutable
public interface LogicalVolumeGroup {
    public String getName();

    public Set<String> getPhysicalVolumes();

    public Map<String, Set<String>> getLogicalVolumes();
}

