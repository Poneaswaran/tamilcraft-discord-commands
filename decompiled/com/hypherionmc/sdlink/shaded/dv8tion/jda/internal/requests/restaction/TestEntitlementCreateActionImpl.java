/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.entities.Entitlement;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Request;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Response;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.TestEntitlementCreateAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.okhttp3.RequestBody;

public class TestEntitlementCreateActionImpl
extends RestActionImpl<Entitlement>
implements TestEntitlementCreateAction {
    private long skuId;
    private long ownerId;
    private TestEntitlementCreateAction.OwnerType type;

    public TestEntitlementCreateActionImpl(JDA api, long skuId, long ownerId, TestEntitlementCreateAction.OwnerType type) {
        super(api, Route.Applications.CREATE_TEST_ENTITLEMENT.compile(api.getSelfUser().getApplicationId()));
        this.skuId = skuId;
        this.ownerId = ownerId;
        this.type = type;
    }

    @Override
    @Nonnull
    public TestEntitlementCreateAction setSkuId(long skuId) {
        this.skuId = skuId;
        return this;
    }

    @Override
    @Nonnull
    public TestEntitlementCreateAction setOwnerId(long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    @Nonnull
    public TestEntitlementCreateAction setOwnerType(@Nonnull TestEntitlementCreateAction.OwnerType type) {
        Checks.notNull((Object)type, "type");
        this.type = type;
        return this;
    }

    @Override
    protected void handleSuccess(Response response, Request<Entitlement> request) {
        DataObject object = response.getObject();
        request.onSuccess(this.api.getEntityBuilder().createEntitlement(object));
    }

    @Override
    protected RequestBody finalizeData() {
        DataObject object = DataObject.empty();
        object.put("sku_id", this.skuId);
        object.put("owner_id", this.ownerId);
        object.put("owner_type", this.type.getKey());
        return this.getRequestBody(object);
    }
}

