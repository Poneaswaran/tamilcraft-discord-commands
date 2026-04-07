/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.managers;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.OnlineStatus;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Activity;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;

public interface Presence {
    @Nonnull
    public JDA getJDA();

    @Nonnull
    public OnlineStatus getStatus();

    @Nullable
    public Activity getActivity();

    public boolean isIdle();

    public void setStatus(@Nullable OnlineStatus var1);

    public void setActivity(@Nullable Activity var1);

    public void setIdle(boolean var1);

    public void setPresence(@Nullable OnlineStatus var1, @Nullable Activity var2, boolean var3);

    public void setPresence(@Nullable OnlineStatus var1, @Nullable Activity var2);

    public void setPresence(@Nullable OnlineStatus var1, boolean var2);

    public void setPresence(@Nullable Activity var1, boolean var2);
}

