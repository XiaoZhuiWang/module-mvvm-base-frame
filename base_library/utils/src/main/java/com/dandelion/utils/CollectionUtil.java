package com.dandelion.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionUtil {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static <T> List<List<T>> getGroupList(int num, List<T> list) {
        List<List<T>> result = new ArrayList<>();
        if (!CollectionUtil.isEmpty(list)) {
            int size = list.size();
            int page = (int) Math.ceil((double) (size) / num);
            for (int i = 0; i < page; i++) {
                int start = i * num;
                if (start > size - 1) {
                    start = size - 1;
                }
                int end = i * num + num;
                if (end > size) {
                    end = size;
                }
                result.add(list.subList(start, end));
            }
        }
        return result;
    }

    /**
     * 比较两个list是否相等
     *
     * @param list1
     * @param list2
     * @return
     */
    public static boolean isEqual(List<String> list1, List<String> list2) {
        if(list1 == null && list2 == null){
            return true;
        }

        if ( (list1 == null && list2 != null)
                || (list1 != null && list2 == null) ) {
            return false;
        }

        // 大小比较
        if (list1.size() != list2.size()) {
            return false;
        }

        String[] arr1 = list1.toArray(new String[]{});
        String[] arr2 = list2.toArray(new String[]{});
        Arrays.sort(arr1);
        Arrays.sort(arr1);
        return Arrays.equals(arr1,arr2);
    }

}
