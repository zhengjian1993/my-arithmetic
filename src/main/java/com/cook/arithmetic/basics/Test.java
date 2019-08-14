package com.cook.arithmetic.basics;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ${DESCRIPTION}
 *
 * @author zhengjian
 * @date 2018 -05 -16 17:30
 */
public class Test {
    /**
     * 第一题
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        int length = intervals.length;
        if (length == 0) {
            return new int[][]{};
        }
        int[][] result = new int[length + 1][];
        int index = 0;
        result[0] = intervals[0];
        for (int i = 1; i < length; i++) {
            int[] n = result[index];
            int[] m = intervals[i];
            int[] h = new int[2];
            if (n[1] >= m[0]) {
                h[0] = n[0];
                h[1] = Math.max(n[1], m[1]);
                result[index] = h;
            } else {
                result[++index] = m;
            }
        }
        return result;
    }


    /**
     * 第二题
     * @param word
     * @param abbr
     * @return
     */
    public boolean valid(String word, String abbr) {
        int wIndex = 0;
        int aIndex = 0;
        int wLength = word.length();
        int aLength = abbr.length();
        while (wIndex < wLength && aIndex < aLength) {
            char w = word.charAt(wIndex);
            char a = abbr.charAt(aIndex);
            if (Character.isDigit(a)) {
                String numberStr = getNumber(abbr, aIndex);
                int numberLength = numberStr.length();
                int number = Integer.valueOf(numberStr);
                wIndex += number;
                aIndex += numberLength;
            } else {
                if (w == a) {
                    wIndex ++;
                    aIndex ++;
                } else {
                    return false;
                }
            }
        }
        return aIndex == aLength && wIndex == wLength;
    }

    public static String getNumber(String abbr, int index) {
        int n = index;
        while (n < abbr.length() && Character.isDigit(abbr.charAt(n))) {
            n++;
        }
        return abbr.substring(index, n);
    }



    /**
     * 第三题
     */
    public int money = 0;

    public int steal(int[] nums) {
        steal(nums, 1, nums[0], true, true);
        steal(nums, 1, 0, false, false);
        return money;
    }

    public void steal(int[] nums, int index, int num, boolean prefixSteal, boolean firstSteal) {
        if (index < nums.length - 1) {
            if (!prefixSteal) {
                steal(nums, index + 1, num + nums[index], true, firstSteal);
            }
            steal(nums, index + 1, num, false, firstSteal);
        } else if (index == nums.length - 1) {
            if (!firstSteal && !prefixSteal) {
                num += nums[index];
            }
            money = num > money ? num : money;
        }
    }

    /*public static void main(String[] args) {
        int[][] a = new int[][] {{1,4}, {2, 8}, {8, 22}};
        int[][] b = merge(a);
        for(int i = 0; i < b.length; i ++) {
            if (b[i] != null) {
                for (int j = 0; j < b[i].length; j++) {
                    System.out.println(b[i][j]);
                }
            }
        }
    }*/

    /*public static void main(String[] args) {
        System.out.println(valid("aaaaabbb", "aa3bbb"));
    }*/

    /*public static void main(String[] args) {
        System.out.println(steal(new int[]{11, 2, 3, 4, 1, 11}));
    }*/
}
