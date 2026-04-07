/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Closure;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Factory;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.ChainedTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.CloneTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.ClosureTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.ConstantTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.EqualPredicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.ExceptionTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.FactoryTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.IfTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.InstantiateTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.InvokerTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.MapTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.NOPTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.PredicateTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.StringValueTransformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.SwitchTransformer;
import java.util.Collection;
import java.util.Map;

public class TransformerUtils {
    private TransformerUtils() {
    }

    public static <I, O> Transformer<I, O> exceptionTransformer() {
        return ExceptionTransformer.exceptionTransformer();
    }

    public static <I, O> Transformer<I, O> nullTransformer() {
        return ConstantTransformer.nullTransformer();
    }

    public static <T> Transformer<T, T> nopTransformer() {
        return NOPTransformer.nopTransformer();
    }

    public static <T> Transformer<T, T> cloneTransformer() {
        return CloneTransformer.cloneTransformer();
    }

    public static <I, O> Transformer<I, O> constantTransformer(O constantToReturn) {
        return ConstantTransformer.constantTransformer(constantToReturn);
    }

    public static <T> Transformer<T, T> asTransformer(Closure<? super T> closure) {
        return ClosureTransformer.closureTransformer(closure);
    }

    public static <T> Transformer<T, Boolean> asTransformer(Predicate<? super T> predicate) {
        return PredicateTransformer.predicateTransformer(predicate);
    }

    public static <I, O> Transformer<I, O> asTransformer(Factory<? extends O> factory2) {
        return FactoryTransformer.factoryTransformer(factory2);
    }

    public static <T> Transformer<T, T> chainedTransformer(Transformer<? super T, ? extends T> ... transformers) {
        return ChainedTransformer.chainedTransformer(transformers);
    }

    public static <T> Transformer<T, T> chainedTransformer(Collection<? extends Transformer<? super T, ? extends T>> transformers) {
        return ChainedTransformer.chainedTransformer(transformers);
    }

    public static <T> Transformer<T, T> ifTransformer(Predicate<? super T> predicate, Transformer<? super T, ? extends T> trueTransformer) {
        return IfTransformer.ifTransformer(predicate, trueTransformer);
    }

    public static <I, O> Transformer<I, O> ifTransformer(Predicate<? super I> predicate, Transformer<? super I, ? extends O> trueTransformer, Transformer<? super I, ? extends O> falseTransformer) {
        return IfTransformer.ifTransformer(predicate, trueTransformer, falseTransformer);
    }

    @Deprecated
    public static <I, O> Transformer<I, O> switchTransformer(Predicate<? super I> predicate, Transformer<? super I, ? extends O> trueTransformer, Transformer<? super I, ? extends O> falseTransformer) {
        return SwitchTransformer.switchTransformer(new Predicate[]{predicate}, new Transformer[]{trueTransformer}, falseTransformer);
    }

    public static <I, O> Transformer<I, O> switchTransformer(Predicate<? super I>[] predicates, Transformer<? super I, ? extends O>[] transformers) {
        return SwitchTransformer.switchTransformer(predicates, transformers, null);
    }

    public static <I, O> Transformer<I, O> switchTransformer(Predicate<? super I>[] predicates, Transformer<? super I, ? extends O>[] transformers, Transformer<? super I, ? extends O> defaultTransformer) {
        return SwitchTransformer.switchTransformer(predicates, transformers, defaultTransformer);
    }

    public static <I, O> Transformer<I, O> switchTransformer(Map<Predicate<I>, Transformer<I, O>> predicatesAndTransformers) {
        return SwitchTransformer.switchTransformer(predicatesAndTransformers);
    }

    public static <I, O> Transformer<I, O> switchMapTransformer(Map<I, Transformer<I, O>> objectsAndTransformers) {
        if (objectsAndTransformers == null) {
            throw new NullPointerException("The object and transformer map must not be null");
        }
        Transformer<I, O> def = objectsAndTransformers.remove(null);
        int size = objectsAndTransformers.size();
        Transformer[] trs = new Transformer[size];
        Predicate[] preds = new Predicate[size];
        int i = 0;
        for (Map.Entry<I, Transformer<I, O>> entry : objectsAndTransformers.entrySet()) {
            preds[i] = EqualPredicate.equalPredicate(entry.getKey());
            trs[i++] = entry.getValue();
        }
        return TransformerUtils.switchTransformer(preds, trs, def);
    }

    public static <T> Transformer<Class<? extends T>, T> instantiateTransformer() {
        return InstantiateTransformer.instantiateTransformer();
    }

    public static <T> Transformer<Class<? extends T>, T> instantiateTransformer(Class<?>[] paramTypes, Object[] args2) {
        return InstantiateTransformer.instantiateTransformer(paramTypes, args2);
    }

    public static <I, O> Transformer<I, O> mapTransformer(Map<? super I, ? extends O> map) {
        return MapTransformer.mapTransformer(map);
    }

    public static <I, O> Transformer<I, O> invokerTransformer(String methodName) {
        return InvokerTransformer.invokerTransformer(methodName, null, null);
    }

    public static <I, O> Transformer<I, O> invokerTransformer(String methodName, Class<?>[] paramTypes, Object[] args2) {
        return InvokerTransformer.invokerTransformer(methodName, paramTypes, args2);
    }

    public static <T> Transformer<T, String> stringValueTransformer() {
        return StringValueTransformer.stringValueTransformer();
    }
}

