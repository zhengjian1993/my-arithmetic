package com.cook.arithmetic.expand;

import org.springframework.util.Assert;

/**
 * 动态规划解决扔鸡蛋问题
 *
 * 有N个鸡蛋，从M层楼上往下扔，以此来测试鸡蛋的硬度。比如鸡蛋在第9层没有摔碎，在第10层摔碎了，那么鸡蛋不会摔碎的临界点就是9层。
 * 问：如何用最少的尝试次数，测试出鸡蛋不会摔碎的临界点？
 *  F（M，N）= Min（Max（ F（M-X，N）+ 1， F（X-1，N-1） + 1）），1<=X<=M
 *  M 表示楼层 N 表示鸡蛋个数
 * @author zhengjian
 * @date 2018 -07 -26 9:52
 */
public class Eggs {

    /**
     * 递归寻找，时间复杂度为指数型
     * @param floorNum
     * @param eggsNum
     * @param isDebug
     * @return
     */
    public int recursionMinSteps(int floorNum, int eggsNum, boolean isDebug){
        if (floorNum < 1 || eggsNum < 1){
            return 0;
        }
        if (floorNum == 1){
            return 1;
        }
        if (eggsNum == 1){
            return floorNum;
        }
        int minSteps = floorNum;
        for (int i = 1; i <= floorNum; i++){
            int brokenSteps = recursionMinSteps(i - 1, eggsNum - 1, false);
            int unbrokenSteps = recursionMinSteps(floorNum - i, eggsNum, false);
            int maxStep = Math.max(brokenSteps, unbrokenSteps) + 1;
            if (isDebug) {
                System.out.println("x = " + i + "层时，最多需要测" + maxStep);
            }
            minSteps = Math.min(minSteps, maxStep);
        }
        return minSteps;
    }


    /**
     * 时间复杂度  m*n*k
     * 空间复杂度  m*n
     * @param floorNum
     * @param eggNum
     * @return
     */
    public int getMinSteps(int floorNum, int eggNum){
        if (eggNum < 1 || floorNum < 1){
            return 0;
        }
        int[][] cache = new int[eggNum + 1][floorNum + 1];
        for(int i = 1; i<= eggNum; i++){
            for(int j = 1; j <= floorNum; j++){
                cache[i][j] = j;
            }
        }
        for (int n = 2; n <= eggNum; n++){
            for (int m = 1; m <= floorNum; m++){
                for (int k = 1; k < m; k++){
                    //F（M，N）= Min（Max（ F（M-X，N）+ 1， F（X-1，N-1） + 1）），1<=X<=M
                    cache[n][m] = Math.min(cache[n][m], 1 + Math.max(cache[n - 1][k - 1], cache[n][m - k]));
                }
            }
        }
        return cache[eggNum][floorNum];
    }

    /**
     * 时间复杂度  m*n*k
     * 空间复杂度   m
     * @param floorNum
     * @param eggNum
     * @return
     */
    public int getMinStepsOptimize(int floorNum, int eggNum){
        if (eggNum < 1 || floorNum < 1){
            return 0;
        }
        int[] preCache;
        int[] currentCache = new int[floorNum + 1];
        for(int i = 1; i<= floorNum; i++){
            currentCache[i] = i;
        }
        for (int n = 2; n <= eggNum; n++){
            preCache = currentCache.clone();
            for(int i = 1; i<= floorNum; i++){
                currentCache[i] = i;
            }
            for (int m = 1; m <= floorNum; m++){
                for (int k = 1; k < m; k++){
                    //F（M，N）= Min（Max（ F（M-X，N）+ 1， F（X-1，N-1） + 1）），1<=X<=M
                    currentCache[m] = Math.min(currentCache[m], 1 + Math.max(preCache[k - 1], currentCache[m - k]));
                }
            }
        }
        return currentCache[floorNum];
    }

    public static void main(String[] args) {
        Eggs eggs = new Eggs();
        for (int i = 2; i < 10 ;i++){
            for (int j = 2; j < 10 ;j++){
                int one = eggs.recursionMinSteps(j ,i, false);
                int two = eggs.getMinSteps(j ,i);
                int three = eggs.getMinStepsOptimize(j, i);
                System.out.println("floor = " + j + "; eggs = " + i
                        + "; one = " + one + ";two = " + two + ";three = " + three
                        + "; flag = " + (one == two && two == three));
            }
        }
    }
}
