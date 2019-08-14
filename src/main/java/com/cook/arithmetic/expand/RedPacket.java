package com.cook.arithmetic.expand;


import java.util.Random;

/**
 * 红包
 *
 * @author zhengjian
 * @date 2018 -05 -16 17:30
 */
public class RedPacket {
    private Random random = new Random();

    private static final int LEFT = 5;
    private static final int RIGHT = 20;
    private static final int LEN = 8;
    private static final int NUM = 40;

    public static void main(String[] args) {
        RedPacket test = new RedPacket();
        for (int i = 0; i < 1000; i++) {
            test.say2();
        }
    }

    public void say2() {
        int num = NUM;
        int[] numbers = new int[LEN];
        for (int i = 0; i < LEN; i++) {
            int number;
            if (i < LEN - 1) {
                int surplus = LEN - i;
                int n = RIGHT * surplus - num;
                int m = num - LEFT * surplus;
                int x = RIGHT - LEFT;
                int left = n > x ? LEFT : LEFT + x - n;
                int right = m > x ? RIGHT : RIGHT - x + m;
                number = getRandom(left, right);
            } else {
                number = num;
            }
            numbers[i] = number;
            num -= number;
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < LEN; i++) {
            s.append(numbers[i]).append(",");
        }
        System.out.println(s.toString());
    }

    public void say() {
        int num = NUM;
        int[] numbers = new int[LEN];
        for (int i = 0; i < LEN; i++) {
            int number = getRandom(LEFT, RIGHT);
            int surplus = LEN - i - 1;
            if (i < LEN - 1) {
                int last = (num - number) / surplus;
                while (last > RIGHT || last < LEFT) {
                    number = getRandom(LEFT, RIGHT);
                    last = (num - number) / surplus;
                }
                if (last == RIGHT) {
                    number = RIGHT;
                }
                if (last == LEFT) {
                    number = LEFT;
                }
            } else {
                number = num;
            }
            numbers[i] = number;
            num -= number;
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < LEN; i++) {
            s.append(numbers[i]).append(",");
        }
        System.out.println(s.toString());
    }

    private int getRandom(int left, int right) {
        int n = right - left + 1;
        return random.nextInt(n) + left;
    }

}
