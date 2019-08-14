package com.cook.arithmetic.string;

/**
 * 给定一个字符串 (s) 和一个字符模式 (p)。实现支持 '.' 和 '*' 的正则表达式匹配。
 * '.' 匹配任意单个字符。
 * '*' 匹配零个或多个前面的元素。
 * 匹配应该覆盖整个字符串 (s) ，而不是部分字符串。
 * 说明:
 * s 可能为空，且只包含从 a-z 的小写字母。
 * p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
 *
 * @author zhengjian
 * @date 2019-04-26 13:24
 */
public class Regular {
    /**
     * 状态空间
      */
    private Boolean[][] memo;

    public boolean isMatch(String s, String p) {
        memo = new Boolean[s.length() + 1][p.length() + 1];
        return match(0, 0, s, p);
    }

    private boolean match(int i, int j, String s, String p) {
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        boolean ans;
        if (j == p.length()){
            ans = i == s.length();
        } else{
            boolean curMatch = (i < s.length() &&
                    (p.charAt(j) == s.charAt(i) ||
                            p.charAt(j) == '.'));

            if (j + 1 < p.length() && p.charAt(j+1) == '*'){
                ans = (match(i, j+2, s, p) ||
                        curMatch && match(i+1, j, s, p));
            } else {
                ans = curMatch && match(i+1, j+1, s, p);
            }
        }
        memo[i][j] = ans;
        return ans;
    }

    public boolean isRecursionMatch(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        if (pLen <= 0) {
            return sLen <= 0;
        }
        boolean match = sLen > 0 && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        if (pLen > 1 && p.charAt(1) == '*') {
            return isMatch(s, p.substring(2)) || (match && isMatch(s.substring(1), p));
        } else {
            return match && isMatch(s.substring(1), p.substring(1));
        }
    }

    private boolean isMatch(String s, String p, int n, int m) {
        int sLen = s.length();
        int pLen = p.length();
        int i = n;
        int j = m;
        for (; j < pLen && i < sLen; j++) {
            char sc = s.charAt(i);
            char pc = p.charAt(j);
            if (sc == pc || '.' == pc) {
                i++;
            }  else {
                if ('*' != pc) {
                    continue;
                }
                return (isMatch(s, p, i + 1, j)
                        || isMatch(s, p, i, j + 1)
                        || isMatch(s, p, i + 1, j + 1));
            }
        }
        if (sLen == i) {
            int h = j;
            while (h < pLen && '*' == p.charAt(h)) {
                h++;
            }
            if (h == pLen) {
                return true;
            }
        }
        return i == sLen && j == pLen;
    }

    public static void main(String[] args) {
        Regular regular = new Regular();
        System.out.println(regular.isMatch("abc", "abc"));
    }
}
