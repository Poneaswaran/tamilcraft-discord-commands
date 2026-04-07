/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.PermissionOverride;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;

public class PermOverrideData
implements SerializableData {
    public static final int ROLE_TYPE = 0;
    public static final int MEMBER_TYPE = 1;
    public final int type;
    public final long id;
    public final long allow;
    public final long deny;

    public PermOverrideData(int type, long id, long allow, long deny) {
        this.type = type;
        this.id = id;
        this.allow = allow;
        this.deny = deny & (allow ^ 0xFFFFFFFFFFFFFFFFL);
    }

    public PermOverrideData(PermissionOverride override) {
        this.id = override.getIdLong();
        this.type = override.isMemberOverride() ? 1 : 0;
        this.allow = override.getAllowedRaw();
        this.deny = override.getDeniedRaw();
    }

    @Override
    @Nonnull
    public DataObject toData() {
        DataObject o = DataObject.empty();
        o.put("type", this.type);
        o.put("id", this.id);
        o.put("allow", this.allow);
        o.put("deny", this.deny);
        return o;
    }

    public int hashCode() {
        return Long.hashCode(this.id);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PermOverrideData)) {
            return false;
        }
        PermOverrideData other = (PermOverrideData)obj;
        return other.id == this.id;
    }
}

