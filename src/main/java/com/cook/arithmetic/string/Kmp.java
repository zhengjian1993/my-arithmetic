package com.cook.arithmetic.string;

/**
 * 字符串kmp算法
 *
 * @author zhengjian
 * @date 2019-04-22 13:19
 */
public class Kmp {
    private static void buildNext(int[] next, String needle) {
        next[0] = -1;
        for (int i = 1; i < next.length; i ++) {
            int n = next[i - 1];
            while (needle.charAt(i) != needle.charAt(n + 1)) {
                if (n < 0) {
                    break;
                }
                n = next[n];
            }
            if (needle.charAt(i) == needle.charAt(n + 1)) {
                n ++;
            }
            next[i] = n;
        }
    }

    public static int strStr(String haystack, String needle) {
        int hLen = haystack.length();
        int nLen = needle.length();
        if (nLen == 0) {
            return 0;
        }
        int i = 0;
        int[] next = new int[nLen];
        buildNext(next, needle);
        while (i < hLen - nLen + 1) {
            for (int j = 0; j < nLen; j++) {
                char ha = haystack.charAt(i + j);
                char ne = needle.charAt(j);
                if (ha == ne) {
                    if (j == nLen - 1) {
                        return i;
                    }
                } else {
                    if (j > 0) {
                        i += j - next[j - 1] - 1;
                    } else {
                        i ++;
                    }
                    break;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String a = "mississippi";
        String b = "issip";
        System.out.println(Kmp.strStr(a, b));
    }
}
