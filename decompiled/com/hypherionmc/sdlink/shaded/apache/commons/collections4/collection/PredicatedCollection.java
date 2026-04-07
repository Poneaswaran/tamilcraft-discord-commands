/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.apache.commons.collections4.collection;

import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Bag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.MultiSet;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.Predicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.HashBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.bag.PredicatedBag;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.collection.AbstractCollectionDecorator;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.functors.NotNullPredicate;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.list.PredicatedList;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.multiset.HashMultiSet;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.multiset.PredicatedMultiSet;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.queue.PredicatedQueue;
import com.hypherionmc.sdlink.shaded.apache.commons.collections4.set.PredicatedSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class PredicatedCollection<E>
extends AbstractCollectionDecorator<E> {
    private static final long serialVersionUID = -5259182142076705162L;
    protected final Predicate<? super E> predicate;

    public static <E> Builder<E> builder(Predicate<? super E> predicate) {
        return new Builder<E>(predicate);
    }

    public static <E> Builder<E> notNullBuilder() {
        return new Builder(NotNullPredicate.notNullPredicate());
    }

    public static <T> PredicatedCollection<T> predicatedCollection(Collection<T> coll, Predicate<? super T> predicate) {
        return new PredicatedCollection<T>(coll, predicate);
    }

    protected PredicatedCollection(Collection<E> coll, Predicate<? super E> predicate) {
        super(coll);
        if (predicate == null) {
            throw new NullPointerException("Predicate must not be null.");
        }
        this.predicate = predicate;
        for (E item : coll) {
            this.validate(item);
        }
    }

    protected void validate(E object) {
        if (!this.predicate.evaluate(object)) {
            throw new IllegalArgumentException("Cannot add Object '" + object + "' - Predicate '" + this.predicate + "' rejected it");
        }
    }

    @Override
    public boolean add(E object) {
        this.validate(object);
        return this.decorated().add(object);
    }

    @Override
    public boolean addAll(Collection<? extends E> coll) {
        for (E item : coll) {
            this.validate(item);
        }
        return this.decorated().addAll(coll);
    }

    public static class Builder<E> {
        private final Predicate<? super E> predicate;
        private final List<E> accepted = new ArrayList();
        private final List<E> rejected = new ArrayList();

        public Builder(Predicate<? super E> predicate) {
            if (predicate == null) {
                throw new NullPointerException("Predicate must not be null");
            }
            this.predicate = predicate;
        }

        public Builder<E> add(E item) {
            if (this.predicate.evaluate(item)) {
                this.accepted.add(item);
            } else {
                this.rejected.add(item);
            }
            return this;
        }

        public Builder<E> addAll(Collection<? extends E> items) {
            if (items != null) {
                for (E item : items) {
                    this.add(item);
                }
            }
            return this;
        }

        public List<E> createPredicatedList() {
            return this.createPredicatedList(new ArrayList());
        }

        public List<E> createPredicatedList(List<E> list) {
            if (list == null) {
                throw new NullPointerException("List must not be null.");
            }
            PredicatedList<E> predicatedList = PredicatedList.predicatedList(list, this.predicate);
            predicatedList.addAll(this.accepted);
            return predicatedList;
        }

        public Set<E> createPredicatedSet() {
            return this.createPredicatedSet(new HashSet());
        }

        public Set<E> createPredicatedSet(Set<E> set) {
            if (set == null) {
                throw new NullPointerException("Set must not be null.");
            }
            PredicatedSet<E> predicatedSet = PredicatedSet.predicatedSet(set, this.predicate);
            predicatedSet.addAll(this.accepted);
            return predicatedSet;
        }

        public MultiSet<E> createPredicatedMultiSet() {
            return this.createPredicatedMultiSet(new HashMultiSet());
        }

        public MultiSet<E> createPredicatedMultiSet(MultiSet<E> multiset) {
            if (multiset == null) {
                throw new NullPointerException("MultiSet must not be null.");
            }
            PredicatedMultiSet<E> predicatedMultiSet = PredicatedMultiSet.predicatedMultiSet(multiset, this.predicate);
            predicatedMultiSet.addAll(this.accepted);
            return predicatedMultiSet;
        }

        public Bag<E> createPredicatedBag() {
            return this.createPredicatedBag(new HashBag());
        }

        public Bag<E> createPredicatedBag(Bag<E> bag) {
            if (bag == null) {
                throw new NullPointerException("Bag must not be null.");
            }
            PredicatedBag<E> predicatedBag = PredicatedBag.predicatedBag(bag, this.predicate);
            predicatedBag.addAll(this.accepted);
            return predicatedBag;
        }

        public Queue<E> createPredicatedQueue() {
            return this.createPredicatedQueue(new LinkedList());
        }

        public Queue<E> createPredicatedQueue(Queue<E> queue) {
            if (queue == null) {
                throw new NullPointerException("queue must not be null");
            }
            PredicatedQueue<E> predicatedQueue = PredicatedQueue.predicatedQueue(queue, this.predicate);
            predicatedQueue.addAll(this.accepted);
            return predicatedQueue;
        }

        public Collection<E> rejectedElements() {
            return Collections.unmodifiableCollection(this.rejected);
        }
    }
}

