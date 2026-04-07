/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.restaction.order;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.JDA;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.Route;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.requests.restaction.order.OrderAction;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.requests.RestActionImpl;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public abstract class OrderActionImpl<T, M extends OrderAction<T, M>>
extends RestActionImpl<Void>
implements OrderAction<T, M> {
    protected final List<T> orderList = new ArrayList<T>();
    protected final boolean ascendingOrder;
    protected int selectedPosition = -1;

    public OrderActionImpl(JDA api, Route.CompiledRoute route) {
        this(api, true, route);
    }

    public OrderActionImpl(JDA api, boolean ascendingOrder, Route.CompiledRoute route) {
        super(api, route);
        this.ascendingOrder = ascendingOrder;
    }

    @Override
    @Nonnull
    public M setCheck(BooleanSupplier checks) {
        return (M)((OrderAction)super.setCheck(checks));
    }

    @Override
    @Nonnull
    public M timeout(long timeout2, @Nonnull TimeUnit unit) {
        return (M)((OrderAction)super.timeout(timeout2, unit));
    }

    @Override
    @Nonnull
    public M deadline(long timestamp) {
        return (M)((OrderAction)super.deadline(timestamp));
    }

    @Override
    public boolean isAscendingOrder() {
        return this.ascendingOrder;
    }

    @Override
    @Nonnull
    public List<T> getCurrentOrder() {
        return Collections.unmodifiableList(this.orderList);
    }

    @Override
    @Nonnull
    public M selectPosition(int selectedPosition) {
        Checks.notNegative(selectedPosition, "Provided selectedPosition");
        Checks.check(selectedPosition < this.orderList.size(), "Provided selectedPosition is too big and is out of bounds. selectedPosition: " + selectedPosition);
        this.selectedPosition = selectedPosition;
        return (M)this;
    }

    @Override
    @Nonnull
    public M selectPosition(@Nonnull T selectedEntity) {
        Checks.notNull(selectedEntity, "Channel");
        this.validateInput(selectedEntity);
        return this.selectPosition((T)this.orderList.indexOf(selectedEntity));
    }

    @Override
    public int getSelectedPosition() {
        return this.selectedPosition;
    }

    @Override
    @Nonnull
    public T getSelectedEntity() {
        if (this.selectedPosition == -1) {
            throw new IllegalStateException("No position has been selected yet");
        }
        return this.orderList.get(this.selectedPosition);
    }

    @Override
    @Nonnull
    public M moveUp(int amount) {
        Checks.notNegative(amount, "Provided amount");
        if (this.selectedPosition == -1) {
            throw new IllegalStateException("Cannot move until an item has been selected. Use #selectPosition first.");
        }
        if (this.ascendingOrder) {
            Checks.check(this.selectedPosition - amount >= 0, "Amount provided to move up is too large and would be out of bounds.Selected position: " + this.selectedPosition + " Amount: " + amount + " Largest Position: " + this.orderList.size());
        } else {
            Checks.check(this.selectedPosition + amount < this.orderList.size(), "Amount provided to move up is too large and would be out of bounds.Selected position: " + this.selectedPosition + " Amount: " + amount + " Largest Position: " + this.orderList.size());
        }
        if (this.ascendingOrder) {
            return this.moveTo(this.selectedPosition - amount);
        }
        return this.moveTo(this.selectedPosition + amount);
    }

    @Override
    @Nonnull
    public M moveDown(int amount) {
        Checks.notNegative(amount, "Provided amount");
        if (this.selectedPosition == -1) {
            throw new IllegalStateException("Cannot move until an item has been selected. Use #selectPosition first.");
        }
        if (this.ascendingOrder) {
            Checks.check(this.selectedPosition + amount < this.orderList.size(), "Amount provided to move down is too large and would be out of bounds. Selected position: " + this.selectedPosition + " Amount: " + amount + " Largest Position: " + this.orderList.size());
        } else {
            Checks.check(this.selectedPosition - amount >= 0, "Amount provided to move down is too large and would be out of bounds. Selected position: " + this.selectedPosition + " Amount: " + amount + " Largest Position: " + this.orderList.size());
        }
        if (this.ascendingOrder) {
            return this.moveTo(this.selectedPosition + amount);
        }
        return this.moveTo(this.selectedPosition - amount);
    }

    @Override
    @Nonnull
    public M moveTo(int position) {
        Checks.notNegative(position, "Provided position");
        Checks.check(position < this.orderList.size(), "Provided position is too big and is out of bounds.");
        T selectedItem = this.orderList.remove(this.selectedPosition);
        this.orderList.add(position, selectedItem);
        this.selectedPosition = position;
        return (M)this;
    }

    @Override
    @Nonnull
    public M moveBelow(@Nonnull T other) {
        this.validateInput(other);
        int index = this.getCurrentOrder().indexOf(other);
        this.moveTo(index);
        if (this.isAscendingOrder()) {
            return this.moveDown(1);
        }
        return (M)this;
    }

    @Override
    @Nonnull
    public M moveAbove(@Nonnull T other) {
        this.validateInput(other);
        int index = this.getCurrentOrder().indexOf(other);
        this.moveTo(index);
        if (!this.isAscendingOrder()) {
            return this.moveUp(1);
        }
        return (M)this;
    }

    @Override
    @Nonnull
    public M swapPosition(int swapPosition) {
        Checks.notNegative(swapPosition, "Provided swapPosition");
        Checks.check(swapPosition < this.orderList.size(), "Provided swapPosition is too big and is out of bounds. swapPosition: " + swapPosition);
        T selectedItem = this.orderList.get(this.selectedPosition);
        T swapItem = this.orderList.get(swapPosition);
        this.orderList.set(swapPosition, selectedItem);
        this.orderList.set(this.selectedPosition, swapItem);
        this.selectedPosition = swapPosition;
        return (M)this;
    }

    @Override
    @Nonnull
    public M swapPosition(@Nonnull T swapEntity) {
        Checks.notNull(swapEntity, "Provided swapEntity");
        this.validateInput(swapEntity);
        return this.swapPosition((T)this.orderList.indexOf(swapEntity));
    }

    @Override
    @Nonnull
    public M reverseOrder() {
        Collections.reverse(this.orderList);
        return (M)this;
    }

    @Override
    @Nonnull
    public M shuffleOrder() {
        Collections.shuffle(this.orderList);
        return (M)this;
    }

    @Override
    @Nonnull
    public M sortOrder(@Nonnull Comparator<T> comparator) {
        Checks.notNull(comparator, "Provided comparator");
        this.orderList.sort(comparator);
        return (M)this;
    }

    protected abstract void validateInput(T var1);
}

