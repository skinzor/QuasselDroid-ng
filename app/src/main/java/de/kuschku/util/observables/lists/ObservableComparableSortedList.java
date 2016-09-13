/*
 * QuasselDroid - Quassel client for Android
 * Copyright (C) 2016 Janne Koschinski
 * Copyright (C) 2016 Ken Børge Viktil
 * Copyright (C) 2016 Magnus Fjell
 * Copyright (C) 2016 Martin Sandsmark <martin.sandsmark@kde.org>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.kuschku.util.observables.lists;

import java.util.Comparator;

import de.kuschku.util.observables.callbacks.UICallback;

public class ObservableComparableSortedList<T extends Comparable<? super T>> extends ObservableSortedList<T> implements IObservableList<UICallback, T> {
    public ObservableComparableSortedList() {
        super(new ComparableComparator<>());
    }

    public ObservableComparableSortedList(int capacity) {
        super(new ComparableComparator<>(), capacity);
    }

    public static class ComparableComparator<T extends Comparable<? super T>> implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }

    public static class ItemComparatorWrapper<T> implements Comparator<T> {
        private final AndroidObservableSortedList.ItemComparator<T> itemComparator;

        public ItemComparatorWrapper(AndroidObservableSortedList.ItemComparator<T> itemComparator) {
            this.itemComparator = itemComparator;
        }

        @Override
        public int compare(T o1, T o2) {
            return itemComparator.compare(o1, o2);
        }
    }
}
