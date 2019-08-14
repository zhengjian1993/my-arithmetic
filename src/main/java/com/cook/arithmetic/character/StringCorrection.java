package com.cook.arithmetic.character;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 字符串纠正
 *
 * @author zhengjian
 * @date 2019-01-29 9:54
 */
public class StringCorrection {
    private List<String> words = new ArrayList<>();

    public void init() {
        // 不同的数据库有不同的驱动
        String driverName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.11.217:3316/dtea?useUnicode=true&characterEncoding=UTF8&useSSL=true";
        String user = "zhengj";
        String password = "zhengj";

        try {
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(url, user, password);
            String sqlString = "select name from dim_tea";
            // 编译sql语句
            // 执行查询
            PreparedStatement pst = conn.prepareStatement(sqlString);
            ResultSet rSet = pst.executeQuery();
            while (rSet.next()) {
                words.add(rSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private char[] a = "mitcmu".toCharArray();
    private char[] b = "mtacnu".toCharArray();
    private int n = 6;
    private int m = 6;
    private int minDist = Integer.MAX_VALUE;

    public int fixBT() {
        fix(0, 0, 0);
        return minDist;
    }

    /**
     * 莱文斯坦距离 回溯算法
     * @param i
     * @param j
     * @param edList
     */
    private void fix(int i, int j, int edList) {
        if (i == n || j == m) {
            if (i < n) {
                edList += n - i;
            }
            if (j < m) {
                edList += m - j;
            }
            if (edList < minDist) {
                minDist = edList;
            }
            return;
        }
        if (a[i] == b[j]) {
            fix(i + 1, j + 1, edList);
        } else {
            fix(i, j + 1, edList + 1);
            fix(i + 1, j, edList + 1);
            fix(i + 1, j + 1, edList + 1);
        }
    }

    private String fixWord(String word) {
        String result = "";
        int min = Integer.MAX_VALUE;
        for (String w : words) {
            if (w.startsWith(word)) {
                return w;
            }
            int minDList = fixDP(word.toCharArray(), w.toCharArray());
            if (minDList < min) {
                min = minDList;
                result = w;
            }
        }
        return result;
    }

    /**
     * 莱文斯坦距离 动态规划实现
     * @return 最短距离
     */
    private int fixDP(char[] a, char[] b) {
        int n = a.length;
        int m = b.length;
        int[][] minMatrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            if (b[0] == a[i]) {
                minMatrix[i][0] = i;
            } else if (i != 0) {
                minMatrix[i][0] = minMatrix[i-1][0] + 1;
            } else {
                minMatrix[i][0] = 1;
            }
        }
        for (int j = 0; j < m; j++) {
            if (a[0] == b[j]) {
                minMatrix[0][j] = j;
            } else if (j != 0) {
                minMatrix[0][j] = minMatrix[0][j-1] + 1;
            } else {
                minMatrix[0][j] = 1;
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (a[i] == b[j]) {
                    minMatrix[i][j] = findMin(minMatrix[i-1][j], minMatrix[i][j-1], minMatrix[i-1][j-1]);
                } else {
                    minMatrix[i][j] = findMin(minMatrix[i-1][j], minMatrix[i][j-1], minMatrix[i-1][j-1]) + 1;
                }
            }
        }
        return minMatrix[n-1][m-1];
    }

    private int findMin(int x, int y ,int z) {
        int min = x;
        if (y < min) {
            min = y;
        }
        if (z < min) {
            min = z;
        }
        return min;
    }


    public static void main(String[] args) {
        StringCorrection stringCorrection = new StringCorrection();
        stringCorrection.init();
        Scanner s = new Scanner(System.in);
        while (s.hasNext()) {
            System.out.println(stringCorrection.fixWord(s.next()));
        }
    }
}
