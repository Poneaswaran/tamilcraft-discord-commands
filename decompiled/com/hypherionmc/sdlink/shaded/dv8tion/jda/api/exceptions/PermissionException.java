/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class PermissionException
extends RuntimeException {
    private final Permission permission;

    public PermissionException(String reason) {
        this(Permission.UNKNOWN, reason);
    }

    protected PermissionException(@Nonnull Permission permission) {
        this(permission, "Cannot perform action due to a lack of Permission. Missing permission: " + permission.toString());
    }

    protected PermissionException(@Nonnull Permission permission, String reason) {
        super(reason);
        Checks.notNull((Object)permission, "permission");
        this.permission = permission;
    }

    @Nonnull
    public Permission getPermission() {
        return this.permission;
    }
}

