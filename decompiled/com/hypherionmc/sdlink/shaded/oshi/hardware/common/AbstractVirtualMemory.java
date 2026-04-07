/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.oshi.hardware.common;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.hardware.VirtualMemory;
import com.hypherionmc.sdlink.shaded.oshi.util.FormatUtil;

@ThreadSafe
public abstract class AbstractVirtualMemory
implements VirtualMemory {
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Swap Used/Avail: ");
        sb.append(FormatUtil.formatBytes(this.getSwapUsed()));
        sb.append("/");
        sb.append(FormatUtil.formatBytes(this.getSwapTotal()));
        sb.append(", Virtual Memory In Use/Max=");
        sb.append(FormatUtil.formatBytes(this.getVirtualInUse()));
        sb.append("/");
        sb.append(FormatUtil.formatBytes(this.getVirtualMax()));
        return sb.toString();
    }
}

