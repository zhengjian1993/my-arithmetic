package com.cook.arithmetic.basics;

import java.util.Arrays;

/**
 * 8大排序算法
 *
 * @author zhengjian
 * @date 2018 -05 -18 9:48
 */
public class Sort {
    /**
     * 直接插入排序
     * @param data
     */
    public void insertSort(int[] data) {
        insertSortHelp(data, 1);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 希尔插入排序
     * @param data
     */
    public void shellSort(int[] data){
        int dk = data.length/2;
        while(dk >= 1){
            insertSortHelp(data, dk);
            dk = dk/2;
        }
        System.out.println(Arrays.toString(data));
    }

    /**
     * 选择排序
     * @param data
     */
    public void selectSort(int[] data){
        int n = data.length;
        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                if (data[j] < data[i]){
                    int tmp = data[i];
                    data[i] = data[j];
                    data[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(data));
    }

    public void twoSelectSort(int[] data){
        int n = data.length;
        for(int m = 1; m < n/2 + 1; m++){
            for(int i = m, j = n - m - 2; i < j; i++, j--){
                if (data[i] > data[j]){
                    int tmp = data[i];
                    data[i] = data[j];
                    data[j] = tmp;
                }
                if (data[i] < data[m-1]){
                    int tmp = data[m-1];
                    data[m-1] = data[i];
                    data[i] = tmp;
                }
                if (data[j] > data[n-m-1]){
                    int tmp = data[n-m-1];
                    data[n-m-1] = data[j];
                    data[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(data));
    }

    /**
     * 最优化的冒泡排序
     * @param data
     */
    public void bubbleSort(int[] data){
        int n = data.length;
        int pos = n - 1;
        for(int i = 0; i < n; i++){
            int f = 0;
            boolean exchange = false;
            for(int j = 0; j < pos; j++) {
                if (data[j] > data[j+1]) {
                    int tmp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = tmp;
                    exchange = true;
                    f = j;
                }
            }
            if (!exchange){
                break;
            }
            pos = f;
        }
        System.out.println(Arrays.toString(data));
    }


    /**
     * 鸡尾酒排序
     * @param data
     */
    public void cocktailSort(int[] data){
        int n = data.length;
        int rightPos = n - 1;
        int leftPos = 0;
        for(int i = 0; i < n/2 - 1; i++){
            int f = 0;
            boolean exchange = false;
            for(int j = leftPos; j < rightPos; j++) {
                if (data[j] > data[j+1]) {
                    int tmp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = tmp;
                    exchange = true;
                    f = j;
                }
            }
            if (!exchange){
                break;
            }
            rightPos = f;

            int e = 0;
            exchange = false;
            for(int m = rightPos; m > leftPos; m--) {
                if (data[m] < data[m - 1]) {
                    int tmp = data[m];
                    data[m] = data[m-1];
                    data[m-1] = tmp;
                    exchange = true;
                    e = m;
                }
            }
            if (!exchange){
                break;
            }
            leftPos = e;
        }
        System.out.println(Arrays.toString(data));
    }

    /**
     * 快速排序
     * @param data
     */
    public void quickSort(int[] data){
        if(quickSort(data, 0, data.length - 1)) {
            System.out.println(Arrays.toString(data));
        }
    }

    /**
     * 快速排序递归辅助方法 (挖坑法)
     * @param data  排序数组
     * @param low 开始下标
     * @param high 结束下标
     * @return 是否完成
     */
    private boolean quickSort(int[] data, int low, int high){
        if(low < high) {
            int start = low;
            int end = high;
            int s = data[start];
            while(start < end){
                while (start < end && data[end] >= s) {
                    end--;
                }
                int tmp = data[end];
                data[end] = data[start];
                data[start] = tmp;
                while (start < end && data[start] <= s) {
                    start++;
                }
                tmp = data[end];
                data[end] = data[start];
                data[start] = tmp;
            }
            quickSort(data, low, start - 1);
            quickSort(data, start + 1, high);
        }
        return true;
    }


    /**
     * 直接插入排序辅助
     * @param data
     * @param dk 步长
     */
    private void insertSortHelp(int[] data, int dk){
        int n = data.length;
        for(int i = dk; i< n;i++){
            int l = i - dk;
            int x = data[i];
            while (l >= 0 && data[l] > x) {
                data[l+dk] = data[l];
                data[l] = x;
                l -= dk;
            }
        }
    }



    public static void main(String[] args) {
        Sort sort = new Sort();
        int[] data = new int[]{76, 47, 25, 25, 49, 18, 93, 26, 11, 52, 74, 98, 64, 78, 50, 53, 87, 88, 22, 4, 47, 35, 26, 67, 57, 99, 50, 45, 39, 53, 6, 9, 45, 11, 69, 62, 2, 54, 97, 74, 92, 14, 27, 80, 85, 12, 72, 28, 92, 46, 33, 68, 80, 25, 92, 76, 28, 60, 18, 32, 11, 13, 57, 74, 39, 60, 61, 15, 17, 52, 39, 98, 83, 62, 73, 68, 6, 33, 86, 64, 27, 3, 55, 35, 54, 51, 91, 12, 51, 96, 96, 46, 38, 51, 7, 22, 20, 81, 42, 48};
        int[] insertData = Arrays.copyOf(data, data.length);
        long sTime = System.nanoTime();
        sort.cocktailSort(insertData);
        long eTime = System.nanoTime();
        System.out.println("insertSort耗时：" + (eTime - sTime));
    }
}
