import java.util.Arrays;

public class Sort {

    public static void print(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 冒泡排序
     */
    public static class MaoPaoSort {

        public static void sort(int[] arr) {
            if (arr.length == 0) {
                System.out.println("array is empty");
                return;
            }
            int temp;
            for (int i = 0, length = arr.length; i < length - 1; i++) {
                for (int j = 0; j < length - 1 - i; j++) {
                    if (arr[j] > arr[j + 1]) {
                        temp = arr[j + 1];
                        arr[j + 1] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
            System.out.println("MaoPao sort success!");
            print(arr);
            System.out.println("==============================");
        }

        public static void main(String[] args) {
            int[] arr = {1, 8, 6, 4, 9, 7, 3, 1, 9, 2};
            Sort.MaoPaoSort.sort(arr);
        }
    }

    /**
     * 直接选择排序
     */
    public static class DirectSort {
        /**
         * arr = {3,1,2}时
         * 第一次外层循环 i=1 temp=1
         * 第一次内层循环 j=0 arr[j]=3 -> temp < arr[j] -> arr[j+1] = arr[j] -> arr[1] = arr[0] -> {3,3,2}
         * 第二次内层循环 j=-1 不符合条件退出内层循环
         * 执行 arr[j+1] = temp -> arr[0] = 1 -> {1,3,2}
         * <p>
         * 第二次外层循环 i=2 temp=2
         * 第一次内存循环 j=1 arr[j]=3 -> temp < arr[j] -> arr[j+1] = arr[j] -> arr[2] = arr[1] -> {1,3,3}
         * 第二次内层循环 j=0 arr[j]=1 -> temp > arr[j] 执行 break;
         * 执行arr[j+1] = temp -> arr[1] = temp -> arr[1] = 2 -> {1,2,3}
         * 退出
         */

        public static void sort(int[] arr) {
            if (arr.length == 0) {
                return;
            }
            //temp 为每次要比较的数字 arr[index] 比较范围为 arr[0] - arr[index-1]
            int temp, j;
            for (int i = 1; i < arr.length; i++) {
                temp = arr[i];
                for (j = i - 1; j >= 0; j--) {
                    if (temp >= arr[j]) {
                        break;
                    }
                    arr[j + 1] = arr[j];
                }
                arr[j + 1] = temp;
                System.out.print("第" + i + "趟temp=" + temp + "\t");
                print(arr);
                System.out.println("==============================");
            }
        }

        public static void main(String[] args) {
            int[] arr = {1, 8, 6, 4, 9, 7, 3, 1, 9, 2};
            Sort.DirectSort.sort(arr);
        }
    }

    public static class QuickSort {

        public static void swap(int [] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        public static void sort(int[] arr, int low, int high) {
            if (arr.length == 0 || low >= high) {
                return;
            }
            //三数取中
            int middle = low + (high - low) / 2;
            if (arr[low] > arr[high]) {
                swap(arr, low, high);
            }
            if (arr[middle] > arr[high]) {
                swap(arr, middle, high);
            }
            if (arr[middle] > arr[low]) {
                swap(arr, middle, low);
            }
            //key就是基准位
            int key = arr[low];
            //指针
            int start = low, end = high;

            while (start < end) {
                //先看右边，依次往左递减
                while (key <= arr[end] && start < end) {
                    end --;
                }
                //再看左边，依次往右递增
                while (arr[start] <= key && start < end) {
                    start ++;
                }
                if (start < end) {
                    int temp = arr[end];
                    arr[end] = arr[start];
                    arr[start] = temp;
                }
            }

            arr[low] = arr[start];
            arr[start] = key;

            sort(arr, low, start-1);
            sort(arr, end+1, high);

        }

        public static void main(String[] args) {
            int[] arr = {1, 8, 6, 4, 9, 7, 3, 1, 9, 2};
            Sort.QuickSort.sort(arr, 0, arr.length-1);
            print(arr);
        }
    }

}
