package io.sf;

import java.util.Arrays;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 17:11 2018/9/13
 */
public class DirectSort {

    public static void main(String[] args) {
        int[] arr = {1, 8, 6, 4, 9, 7, 3, 1, 9, 2};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        if (arr.length == 0) {
            return;
        }

        int temp,j;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            for (j = i - 1; j >= 0; j --) {
                if (temp >= arr[j]) {
                    break;
                }
                arr[j + 1] = arr[j];
            }
            // 当j-- j >=0 j的最小值为-1
            arr[j + 1] = temp;
        }

    }

}
