package com.cook.arithmetic.expand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhengjian
 * @date 2019-08-14 13:45
 */
public class Tencent {
    private Random random = new Random();

    public void sum(int n) {
        Map<Integer, AtomicInteger> map = new HashMap<>(4);
        for (int i = 0; i < n; i++) {
            int index = 0;
            while (index == 0) {
                index = random.nextInt(2);
                map.computeIfAbsent(index, v -> new AtomicInteger(1)).incrementAndGet();
            }
        }
        System.out.println(map.get(0));
        System.out.println(map.get(1));
    }

    public static void main(String[] args) {
        Tencent tencent = new Tencent();
        tencent.sum(100000);

    }
}
