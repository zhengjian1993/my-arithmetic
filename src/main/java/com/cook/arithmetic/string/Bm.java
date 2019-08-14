package com.cook.arithmetic.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串匹配bm算法
 *
 * @author zhengjian
 * @date 2019-04-19 14:11
 */
public class Bm {
    public static int strStr(String haystack, String needle) {
        int hLen = haystack.length();
        int nLen = needle.length();
        if (nLen == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>(8);
        int[] matchings = new int[nLen];
        for (int i = 0; i < nLen; i++) {
            matchings[i] = -1;
        }
        boolean[] prefix = new boolean[nLen];
        for (int i = 0; i < nLen; i++) {
            map.put(needle.charAt(i), i);
            int n = i;
            int m = 0;
            if (i < nLen - 1) {
                while (n >= 0 && needle.charAt(n) == needle.charAt(nLen - m - 1)) {
                    n--;
                    m++;
                    matchings[m] = n + 1;
                }
                if (n < 0) {
                    prefix[m] = true;
                }
            }
        }

        int i = 0;
        while (i < hLen - nLen + 1) {
            for (int j = nLen - 1; j >= 0; j--) {
                char ha = haystack.charAt(i + j);
                char ne = needle.charAt(j);
                if (ha == ne) {
                    if (j == 0) {
                        return i;
                    }
                } else {
                    int nIndex = map.get(ha) == null ? -1 : map.get(ha);
                    int matchLen = nLen - j - 1;
                    int mIndex = matchLen > 0 ? matchings[matchLen] : nLen;
                    if (mIndex < 0) {
                        for (int n = matchLen; n >= 0; n--) {
                            if (prefix[n]) {
                                mIndex = n;
                            }
                        }
                    }
                    i += Math.max(j - nIndex, j - mIndex);
                    break;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String a = "mississippi";
        String b = "issi";
        System.out.println(Bm.strStr(a, b));
    }
}
