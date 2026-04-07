/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.platform.win32.COM.WbemcliUtil$WmiQuery
 *  com.sun.jna.platform.win32.COM.WbemcliUtil$WmiResult
 */
package com.hypherionmc.sdlink.shaded.oshi.driver.windows.wmi;

import com.hypherionmc.sdlink.shaded.oshi.annotation.concurrent.ThreadSafe;
import com.hypherionmc.sdlink.shaded.oshi.util.platform.windows.WmiQueryHandler;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import java.util.Collection;
import java.util.Set;

@ThreadSafe
public final class Win32Process {
    private static final String WIN32_PROCESS = "Win32_Process";

    private Win32Process() {
    }

    public static WbemcliUtil.WmiResult<CommandLineProperty> queryCommandLines(Set<Integer> pidsToQuery) {
        StringBuilder sb = new StringBuilder(WIN32_PROCESS);
        if (pidsToQuery != null) {
            boolean first = true;
            for (Integer pid : pidsToQuery) {
                if (first) {
                    sb.append(" WHERE ProcessID=");
                    first = false;
                } else {
                    sb.append(" OR ProcessID=");
                }
                sb.append(pid);
            }
        }
        WbemcliUtil.WmiQuery commandLineQuery = new WbemcliUtil.WmiQuery(sb.toString(), CommandLineProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(commandLineQuery);
    }

    public static WbemcliUtil.WmiResult<ProcessXPProperty> queryProcesses(Collection<Integer> pids) {
        StringBuilder sb = new StringBuilder(WIN32_PROCESS);
        if (pids != null) {
            boolean first = true;
            for (Integer pid : pids) {
                if (first) {
                    sb.append(" WHERE ProcessID=");
                    first = false;
                } else {
                    sb.append(" OR ProcessID=");
                }
                sb.append(pid);
            }
        }
        WbemcliUtil.WmiQuery processQueryXP = new WbemcliUtil.WmiQuery(sb.toString(), ProcessXPProperty.class);
        return WmiQueryHandler.createInstance().queryWMI(processQueryXP);
    }

    public static enum CommandLineProperty {
        PROCESSID,
        COMMANDLINE;

    }

    public static enum ProcessXPProperty {
        PROCESSID,
        NAME,
        KERNELMODETIME,
        USERMODETIME,
        THREADCOUNT,
        PAGEFILEUSAGE,
        HANDLECOUNT,
        EXECUTABLEPATH;

    }
}

