package io.sf.sort;

import java.util.Arrays;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:30 2018/9/13
 */
public class QuickSort {

    public static void swap(int [] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void swap1(int[] arr, int a, int b) {
//        arr[a] = arr[a] + arr[b];
//        arr[b] = arr[a] - arr[b];
//        arr[a] = arr[a] - arr[b];
        arr[a] ^= arr[b];
        arr[b] ^= arr[a];
        arr[a] ^= arr[b];
    }

    public static void sort(int [] arr, int low, int high){
        if (high < low) {
            return;
        }
        //三数取中 并把中位放到数组第0位 返回这个数
        int key = getMiddleNumber(arr, low, high);

        int start = low, end = high;

        while (start < end) {
            /**
             *  基准值key放到第一位了 所以要从右边开始往左找
             */
            while (start < end && arr[end] >= key) {
                end --;
            }
            //交换位置 第一次直接和基准值交换 空出了end这个位置
            arr[start] = arr[end];
            while (start < end && arr[start] <=key) {
                start ++;
            }
            arr[end] = arr[start];
        }
        //当跳出循环的时候start==end
        arr[start] = key;

        sort(arr, low, start-1);
        sort(arr, start+1, high);
    }

    private static int getMiddleNumber(int[] arr, int low, int high) {
        int middle = low + (high - low ) / 2;
        if (arr[low] > arr[high]) {
            swap1(arr, low, high);
        }
        if (arr[middle] > arr[high]) {
            swap1(arr, middle, high);
        }
        if (arr[middle] > arr[low]) {
            swap1(arr, middle, low);
        }
        return arr[low];
    }

    public static void main(String[] args) {
//        int[] arr = {1, 8, 6, 4, 9, 7, 3, 1, 9, 2};
        int[] arr = {12, 85, 25, 16, 88, 23, 49, 95, 17, 61};

        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
