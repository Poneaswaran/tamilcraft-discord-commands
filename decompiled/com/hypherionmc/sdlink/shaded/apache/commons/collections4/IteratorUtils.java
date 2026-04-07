/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Closure;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.CollectionUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ComparatorUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.MapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.OrderedIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.OrderedMapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.ResettableListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Transformer;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.TransformerUtils;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.EqualPredicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ArrayIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ArrayListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.BoundedIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.CollatingIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.EmptyIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.EmptyListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.EmptyMapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.EmptyOrderedIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.EmptyOrderedMapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.EnumerationIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.FilterIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.FilterListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.IteratorChain;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.IteratorEnumeration;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.IteratorIterable;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ListIteratorWrapper;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.LoopingIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.LoopingListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.NodeListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ObjectArrayIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ObjectArrayListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ObjectGraphIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.PeekingIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.PushbackIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.SingletonIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.SingletonListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.SkippingIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.TransformIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.UnmodifiableIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.UnmodifiableListIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.UnmodifiableMapIterator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.iterators.ZippingIterator;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IteratorUtils {
    public static final ResettableIterator EMPTY_ITERATOR = EmptyIterator.RESETTABLE_INSTANCE;
    public static final ResettableListIterator EMPTY_LIST_ITERATOR = EmptyListIterator.RESETTABLE_INSTANCE;
    public static final OrderedIterator EMPTY_ORDERED_ITERATOR = EmptyOrderedIterator.INSTANCE;
    public static final MapIterator EMPTY_MAP_ITERATOR = EmptyMapIterator.INSTANCE;
    public static final OrderedMapIterator EMPTY_ORDERED_MAP_ITERATOR = EmptyOrderedMapIterator.INSTANCE;
    private static final String DEFAULT_TOSTRING_PREFIX = "[";
    private static final String DEFAULT_TOSTRING_SUFFIX = "]";
    private static final String DEFAULT_TOSTRING_DELIMITER = ", ";

    private IteratorUtils() {
    }

    public static <E> ResettableIterator<E> emptyIterator() {
        return EmptyIterator.resettableEmptyIterator();
    }

    public static <E> ResettableListIterator<E> emptyListIterator() {
        return EmptyListIterator.resettableEmptyListIterator();
    }

    public static <E> OrderedIterator<E> emptyOrderedIterator() {
        return EmptyOrderedIterator.emptyOrderedIterator();
    }

    public static <K, V> MapIterator<K, V> emptyMapIterator() {
        return EmptyMapIterator.emptyMapIterator();
    }

    public static <K, V> OrderedMapIterator<K, V> emptyOrderedMapIterator() {
        return EmptyOrderedMapIterator.emptyOrderedMapIterator();
    }

    public static <E> ResettableIterator<E> singletonIterator(E object) {
        return new SingletonIterator<E>(object);
    }

    public static <E> ListIterator<E> singletonListIterator(E object) {
        return new SingletonListIterator<E>(object);
    }

    public static <E> ResettableIterator<E> arrayIterator(E ... array) {
        return new ObjectArrayIterator<E>(array);
    }

    public static <E> ResettableIterator<E> arrayIterator(Object array) {
        return new ArrayIterator(array);
    }

    public static <E> ResettableIterator<E> arrayIterator(E[] array, int start) {
        return new ObjectArrayIterator<E>(array, start);
    }

    public static <E> ResettableIterator<E> arrayIterator(Object array, int start) {
        return new ArrayIterator(array, start);
    }

    public static <E> ResettableIterator<E> arrayIterator(E[] array, int start, int end) {
        return new ObjectArrayIterator<E>(array, start, end);
    }

    public static <E> ResettableIterator<E> arrayIterator(Object array, int start, int end) {
        return new ArrayIterator(array, start, end);
    }

    public static <E> ResettableListIterator<E> arrayListIterator(E ... array) {
        return new ObjectArrayListIterator<E>(array);
    }

    public static <E> ResettableListIterator<E> arrayListIterator(Object array) {
        return new ArrayListIterator(array);
    }

    public static <E> ResettableListIterator<E> arrayListIterator(E[] array, int start) {
        return new ObjectArrayListIterator<E>(array, start);
    }

    public static <E> ResettableListIterator<E> arrayListIterator(Object array, int start) {
        return new ArrayListIterator(array, start);
    }

    public static <E> ResettableListIterator<E> arrayListIterator(E[] array, int start, int end) {
        return new ObjectArrayListIterator<E>(array, start, end);
    }

    public static <E> ResettableListIterator<E> arrayListIterator(Object array, int start, int end) {
        return new ArrayListIterator(array, start, end);
    }

    public static <E> BoundedIterator<E> boundedIterator(Iterator<? extends E> iterator2, long max) {
        return IteratorUtils.boundedIterator(iterator2, 0L, max);
    }

    public static <E> BoundedIterator<E> boundedIterator(Iterator<? extends E> iterator2, long offset, long max) {
        return new BoundedIterator<E>(iterator2, offset, max);
    }

    public static <E> Iterator<E> unmodifiableIterator(Iterator<E> iterator2) {
        return UnmodifiableIterator.unmodifiableIterator(iterator2);
    }

    public static <E> ListIterator<E> unmodifiableListIterator(ListIterator<E> listIterator2) {
        return UnmodifiableListIterator.umodifiableListIterator(listIterator2);
    }

    public static <K, V> MapIterator<K, V> unmodifiableMapIterator(MapIterator<K, V> mapIterator) {
        return UnmodifiableMapIterator.unmodifiableMapIterator(mapIterator);
    }

    public static <E> Iterator<E> chainedIterator(Iterator<? extends E> iterator1, Iterator<? extends E> iterator2) {
        return new IteratorChain<E>(iterator1, iterator2);
    }

    public static <E> Iterator<E> chainedIterator(Iterator<? extends E> ... iterators) {
        return new IteratorChain<E>(iterators);
    }

    public static <E> Iterator<E> chainedIterator(Collection<Iterator<? extends E>> iterators) {
        return new IteratorChain<E>(iterators);
    }

    public static <E> Iterator<E> collatedIterator(Comparator<? super E> comparator, Iterator<? extends E> iterator1, Iterator<? extends E> iterator2) {
        Comparator<? super E> comp = comparator == null ? ComparatorUtils.NATURAL_COMPARATOR : comparator;
        return new CollatingIterator<E>(comp, iterator1, iterator2);
    }

    public static <E> Iterator<E> collatedIterator(Comparator<? super E> comparator, Iterator<? extends E> ... iterators) {
        Comparator<? super E> comp = comparator == null ? ComparatorUtils.NATURAL_COMPARATOR : comparator;
        return new CollatingIterator<E>(comp, iterators);
    }

    public static <E> Iterator<E> collatedIterator(Comparator<? super E> comparator, Collection<Iterator<? extends E>> iterators) {
        Comparator<? super E> comp = comparator == null ? ComparatorUtils.NATURAL_COMPARATOR : comparator;
        return new CollatingIterator<E>(comp, iterators);
    }

    public static <E> Iterator<E> objectGraphIterator(E root, Transformer<? super E, ? extends E> transformer) {
        return new ObjectGraphIterator<E>(root, transformer);
    }

    public static <I, O> Iterator<O> transformedIterator(Iterator<? extends I> iterator2, Transformer<? super I, ? extends O> transform) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        if (transform == null) {
            throw new NullPointerException("Transformer must not be null");
        }
        return new TransformIterator<I, O>(iterator2, transform);
    }

    public static <E> Iterator<E> filteredIterator(Iterator<? extends E> iterator2, Predicate<? super E> predicate) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        if (predicate == null) {
            throw new NullPointerException("Predicate must not be null");
        }
        return new FilterIterator<E>(iterator2, predicate);
    }

    public static <E> ListIterator<E> filteredListIterator(ListIterator<? extends E> listIterator2, Predicate<? super E> predicate) {
        if (listIterator2 == null) {
            throw new NullPointerException("ListIterator must not be null");
        }
        if (predicate == null) {
            throw new NullPointerException("Predicate must not be null");
        }
        return new FilterListIterator<E>(listIterator2, predicate);
    }

    public static <E> ResettableIterator<E> loopingIterator(Collection<? extends E> coll) {
        if (coll == null) {
            throw new NullPointerException("Collection must not be null");
        }
        return new LoopingIterator<E>(coll);
    }

    public static <E> ResettableListIterator<E> loopingListIterator(List<E> list) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        }
        return new LoopingListIterator<E>(list);
    }

    public static NodeListIterator nodeListIterator(NodeList nodeList) {
        if (nodeList == null) {
            throw new NullPointerException("NodeList must not be null");
        }
        return new NodeListIterator(nodeList);
    }

    public static NodeListIterator nodeListIterator(Node node) {
        if (node == null) {
            throw new NullPointerException("Node must not be null");
        }
        return new NodeListIterator(node);
    }

    public static <E> Iterator<E> peekingIterator(Iterator<? extends E> iterator2) {
        return PeekingIterator.peekingIterator(iterator2);
    }

    public static <E> Iterator<E> pushbackIterator(Iterator<? extends E> iterator2) {
        return PushbackIterator.pushbackIterator(iterator2);
    }

    public static <E> SkippingIterator<E> skippingIterator(Iterator<E> iterator2, long offset) {
        return new SkippingIterator<E>(iterator2, offset);
    }

    public static <E> ZippingIterator<E> zippingIterator(Iterator<? extends E> a, Iterator<? extends E> b) {
        return new ZippingIterator<E>(a, b);
    }

    public static <E> ZippingIterator<E> zippingIterator(Iterator<? extends E> a, Iterator<? extends E> b, Iterator<? extends E> c) {
        return new ZippingIterator<E>(a, b, c);
    }

    public static <E> ZippingIterator<E> zippingIterator(Iterator<? extends E> ... iterators) {
        return new ZippingIterator<E>(iterators);
    }

    public static <E> Iterator<E> asIterator(Enumeration<? extends E> enumeration) {
        if (enumeration == null) {
            throw new NullPointerException("Enumeration must not be null");
        }
        return new EnumerationIterator<E>(enumeration);
    }

    public static <E> Iterator<E> asIterator(Enumeration<? extends E> enumeration, Collection<? super E> removeCollection) {
        if (enumeration == null) {
            throw new NullPointerException("Enumeration must not be null");
        }
        if (removeCollection == null) {
            throw new NullPointerException("Collection must not be null");
        }
        return new EnumerationIterator<E>(enumeration, removeCollection);
    }

    public static <E> Enumeration<E> asEnumeration(Iterator<? extends E> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        return new IteratorEnumeration<E>(iterator2);
    }

    public static <E> Iterable<E> asIterable(Iterator<? extends E> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        return new IteratorIterable<E>(iterator2, false);
    }

    public static <E> Iterable<E> asMultipleUseIterable(Iterator<? extends E> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        return new IteratorIterable<E>(iterator2, true);
    }

    public static <E> ListIterator<E> toListIterator(Iterator<? extends E> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        return new ListIteratorWrapper<E>(iterator2);
    }

    public static Object[] toArray(Iterator<?> iterator2) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        List<?> list = IteratorUtils.toList(iterator2, 100);
        return list.toArray();
    }

    public static <E> E[] toArray(Iterator<? extends E> iterator2, Class<E> arrayClass) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        if (arrayClass == null) {
            throw new NullPointerException("Array class must not be null");
        }
        List<E> list = IteratorUtils.toList(iterator2, 100);
        Object[] array = (Object[])Array.newInstance(arrayClass, list.size());
        return list.toArray(array);
    }

    public static <E> List<E> toList(Iterator<? extends E> iterator2) {
        return IteratorUtils.toList(iterator2, 10);
    }

    public static <E> List<E> toList(Iterator<? extends E> iterator2, int estimatedSize) {
        if (iterator2 == null) {
            throw new NullPointerException("Iterator must not be null");
        }
        if (estimatedSize < 1) {
            throw new IllegalArgumentException("Estimated size must be greater than 0");
        }
        ArrayList<E> list = new ArrayList<E>(estimatedSize);
        while (iterator2.hasNext()) {
            list.add(iterator2.next());
        }
        return list;
    }

    public static Iterator<?> getIterator(Object obj) {
        if (obj == null) {
            return IteratorUtils.emptyIterator();
        }
        if (obj instanceof Iterator) {
            return (Iterator)obj;
        }
        if (obj instanceof Iterable) {
            return ((Iterable)obj).iterator();
        }
        if (obj instanceof Object[]) {
            return new ObjectArrayIterator<Object>((Object[])obj);
        }
        if (obj instanceof Enumeration) {
            return new EnumerationIterator((Enumeration)obj);
        }
        if (obj instanceof Map) {
            return ((Map)obj).values().iterator();
        }
        if (obj instanceof NodeList) {
            return new NodeListIterator((NodeList)obj);
        }
        if (obj instanceof Node) {
            return new NodeListIterator((Node)obj);
        }
        if (obj instanceof Dictionary) {
            return new EnumerationIterator(((Dictionary)obj).elements());
        }
        if (obj.getClass().isArray()) {
            return new ArrayIterator(obj);
        }
        try {
            Iterator it;
            Method method = obj.getClass().getMethod("iterator", null);
            if (Iterator.class.isAssignableFrom(method.getReturnType()) && (it = (Iterator)method.invoke(obj, (Object[])null)) != null) {
                return it;
            }
        }
        catch (RuntimeException runtimeException) {
        }
        catch (NoSuchMethodException noSuchMethodException) {
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        catch (InvocationTargetException invocationTargetException) {
            // empty catch block
        }
        return IteratorUtils.singletonIterator(obj);
    }

    public static <E> void forEach(Iterator<E> iterator2, Closure<? super E> closure) {
        if (closure == null) {
            throw new NullPointerException("Closure must not be null");
        }
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                E element = iterator2.next();
                closure.execute(element);
            }
        }
    }

    public static <E> E forEachButLast(Iterator<E> iterator2, Closure<? super E> closure) {
        if (closure == null) {
            throw new NullPointerException("Closure must not be null.");
        }
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                E element = iterator2.next();
                if (iterator2.hasNext()) {
                    closure.execute(element);
                    continue;
                }
                return element;
            }
        }
        return null;
    }

    public static <E> E find(Iterator<E> iterator2, Predicate<? super E> predicate) {
        if (predicate == null) {
            throw new NullPointerException("Predicate must not be null");
        }
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                E element = iterator2.next();
                if (!predicate.evaluate(element)) continue;
                return element;
            }
        }
        return null;
    }

    public static <E> int indexOf(Iterator<E> iterator2, Predicate<? super E> predicate) {
        if (predicate == null) {
            throw new NullPointerException("Predicate must not be null");
        }
        if (iterator2 != null) {
            int index = 0;
            while (iterator2.hasNext()) {
                E element = iterator2.next();
                if (predicate.evaluate(element)) {
                    return index;
                }
                ++index;
            }
        }
        return -1;
    }

    public static <E> boolean matchesAny(Iterator<E> iterator2, Predicate<? super E> predicate) {
        return IteratorUtils.indexOf(iterator2, predicate) != -1;
    }

    public static <E> boolean matchesAll(Iterator<E> iterator2, Predicate<? super E> predicate) {
        if (predicate == null) {
            throw new NullPointerException("Predicate must not be null");
        }
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                E element = iterator2.next();
                if (predicate.evaluate(element)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(Iterator<?> iterator2) {
        return iterator2 == null || !iterator2.hasNext();
    }

    public static <E> boolean contains(Iterator<E> iterator2, Object object) {
        return IteratorUtils.matchesAny(iterator2, EqualPredicate.equalPredicate(object));
    }

    public static <E> E get(Iterator<E> iterator2, int index) {
        int i = index;
        CollectionUtils.checkIndexBounds(i);
        while (iterator2.hasNext()) {
            if (--i == -1) {
                return iterator2.next();
            }
            iterator2.next();
        }
        throw new IndexOutOfBoundsException("Entry does not exist: " + i);
    }

    public static <E> E first(Iterator<E> iterator2) {
        return IteratorUtils.get(iterator2, 0);
    }

    public static int size(Iterator<?> iterator2) {
        int size = 0;
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                iterator2.next();
                ++size;
            }
        }
        return size;
    }

    public static <E> String toString(Iterator<E> iterator2) {
        return IteratorUtils.toString(iterator2, TransformerUtils.stringValueTransformer(), DEFAULT_TOSTRING_DELIMITER, DEFAULT_TOSTRING_PREFIX, DEFAULT_TOSTRING_SUFFIX);
    }

    public static <E> String toString(Iterator<E> iterator2, Transformer<? super E, String> transformer) {
        return IteratorUtils.toString(iterator2, transformer, DEFAULT_TOSTRING_DELIMITER, DEFAULT_TOSTRING_PREFIX, DEFAULT_TOSTRING_SUFFIX);
    }

    public static <E> String toString(Iterator<E> iterator2, Transformer<? super E, String> transformer, String delimiter, String prefix, String suffix) {
        if (transformer == null) {
            throw new NullPointerException("transformer may not be null");
        }
        if (delimiter == null) {
            throw new NullPointerException("delimiter may not be null");
        }
        if (prefix == null) {
            throw new NullPointerException("prefix may not be null");
        }
        if (suffix == null) {
            throw new NullPointerException("suffix may not be null");
        }
        StringBuilder stringBuilder = new StringBuilder(prefix);
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                E element = iterator2.next();
                stringBuilder.append(transformer.transform(element));
                stringBuilder.append(delimiter);
            }
            if (stringBuilder.length() > prefix.length()) {
                stringBuilder.setLength(stringBuilder.length() - delimiter.length());
            }
        }
        stringBuilder.append(suffix);
        return stringBuilder.toString();
    }
}

