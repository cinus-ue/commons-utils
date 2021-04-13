package com.cinus.collection;

import com.cinus.math.MathUtils;

import java.util.*;
import java.util.Map.Entry;

public class CollectionUtils {

    public static <T> String join(Iterable<T> collection, String conjunction) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (T item : collection) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(conjunction);
            }
            sb.append(item);
        }
        return sb.toString();
    }


    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    public static <T> boolean isNotEmpty(T[] array) {
        return false == isEmpty(array);
    }


    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    public static boolean isNotEmpty(Collection<?> collection) {
        return false == isEmpty(collection);
    }


    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


    public static boolean isNotEmpty(Map<?, ?> map) {
        return false == isEmpty(map);
    }


    public static <K, V> Map<K, V> zip(K[] keys, V[] values) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }
        final int size = Math.min(keys.length, values.length);
        final Map<K, V> map = new HashMap<>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }


    public static <K, V> Map<K, V> zip(Collection<K> keys, Collection<V> values) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }
        final List<K> keyList = new ArrayList<>(keys);
        final List<V> valueList = new ArrayList<>(values);
        final int size = Math.min(keys.size(), values.size());
        final Map<K, V> map = new HashMap<>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keyList.get(i), valueList.get(i));
        }
        return map;
    }

    public static <K, V> HashMap<K, V> toMap(Collection<Entry<K, V>> entryCollection) {
        HashMap<K, V> map = new HashMap<>();
        for (Entry<K, V> entry : entryCollection) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }


    public static <E> TreeSet<E> toTreeSet(Collection<E> collection, Comparator<E> comparator) {
        final TreeSet<E> treeSet = new TreeSet<>(comparator);
        for (E e : collection) {
            treeSet.add(e);
        }
        return treeSet;
    }


    public static <E> List<E> sort(Collection<E> collection, Comparator<E> comparator) {
        List<E> list = new ArrayList<>(collection);
        Collections.sort(list, comparator);
        return list;
    }

    public static <E> E randomEle(List<E> list) {
        return randomEle(list, list.size());
    }


    public static <E> E randomEle(List<E> list, int limit) {
        return list.get(MathUtils.randomInt(limit));
    }


    public static <E> List<E> randomEles(List<E> list, int count) {
        final List<E> result = new ArrayList<>(count);
        int limit = list.size();
        while (--count > 0) {
            result.add(randomEle(list, limit));
        }
        return result;
    }


    public static List<Entry<Long, Long>> sortEntrySetToList(Set<Entry<Long, Long>> set) {
        List<Entry<Long, Long>> list = new LinkedList<>(set);
        Collections.sort(list, (o1, o2) -> {
            if (o1.getValue() > o2.getValue()) {
                return 1;
            }
            if (o1.getValue() < o2.getValue()) {
                return -1;
            }
            return 0;
        });
        return list;
    }


    public static <E> List<E> popPart(Stack<E> surplusAlaDatas, int partSize) {
        if (surplusAlaDatas == null || surplusAlaDatas.size() <= 0) {
            return null;
        }
        final List<E> currentAlaDatas = new ArrayList<>();
        int size = surplusAlaDatas.size();
        if (size > partSize) {
            for (int i = 0; i < partSize; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        } else {
            for (int i = 0; i < size; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        }
        return currentAlaDatas;
    }


    public static <E> List<E> popPart(Deque<E> surplusAlaDatas, int partSize) {
        if (surplusAlaDatas == null || surplusAlaDatas.size() <= 0) {
            return null;
        }

        final List<E> currentAlaDatas = new ArrayList<>();
        int size = surplusAlaDatas.size();
        if (size > partSize) {
            for (int i = 0; i < partSize; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        } else {
            for (int i = 0; i < size; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        }
        return currentAlaDatas;
    }


    public static <E> List<E> sub(List<E> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (end < start) {
            return null;
        }
        final int size = list.size();
        if (start >= size) {
            return null;
        }
        if (end > size) {
            end = size;
        }
        return list.subList(start, end);
    }


    public static <E> List<E> sub(Collection<E> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return sub(new ArrayList<>(list), start, end);
    }

}
