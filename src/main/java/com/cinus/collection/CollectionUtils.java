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


    public static <T> List<T> popPart(Stack<T> surplusAlaDatas, int partSize) {
        if (surplusAlaDatas == null || surplusAlaDatas.size() <= 0) {
            return null;
        }
        final List<T> currentAlaDatas = new ArrayList<>();
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


    public static <T> List<T> popPart(Deque<T> surplusAlaDatas, int partSize) {
        if (surplusAlaDatas == null || surplusAlaDatas.size() <= 0) {
            return null;
        }

        final List<T> currentAlaDatas = new ArrayList<>();
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


    public static <T> List<T> sub(List<T> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }
        final int size = list.size();
        if (end > size) {
            if (start >= size) {
                return null;
            }
            end = size;
        }
        return list.subList(start, end);
    }


    public static <T> List<T> sub(Collection<T> list, int start, int end) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return sub(new ArrayList<>(list), start, end);
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


    public static <T> boolean isNotEmpty(Map<?, ?> map) {
        return false == isEmpty(map);
    }


    public static <T, K> Map<T, K> zip(T[] keys, K[] values) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }

        final int size = Math.min(keys.length, values.length);
        final Map<T, K> map = new HashMap<>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }


    public static <T, K> Map<T, K> zip(Collection<T> keys, Collection<K> values) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }

        final List<T> keyList = new ArrayList<>(keys);
        final List<K> valueList = new ArrayList<>(values);

        final int size = Math.min(keys.size(), values.size());
        final Map<T, K> map = new HashMap<>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keyList.get(i), valueList.get(i));
        }

        return map;
    }

    public static <T, K> HashMap<T, K> toMap(Collection<Entry<T, K>> entryCollection) {
        HashMap<T, K> map = new HashMap<>();
        for (Entry<T, K> entry : entryCollection) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }


    public static <T> TreeSet<T> toTreeSet(Collection<T> collection, Comparator<T> comparator) {
        final TreeSet<T> treeSet = new TreeSet<>(comparator);
        for (T t : collection) {
            treeSet.add(t);
        }
        return treeSet;
    }


    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(collection);
        Collections.sort(list, comparator);
        return list;
    }

    public static <T> T randomEle(List<T> list) {
        return randomEle(list, list.size());
    }


    public static <T> T randomEle(List<T> list, int limit) {
        return list.get(MathUtils.randomInt(limit));
    }


    public static <T> List<T> randomEles(List<T> list, int count) {
        final List<T> result = new ArrayList<>(count);
        int limit = list.size();
        while (--count > 0) {
            result.add(randomEle(list, limit));
        }
        return result;
    }

}
