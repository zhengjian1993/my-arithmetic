package com.cook.arithmetic.expand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * ${DESCRIPTION}
 *
 * @author zhengjian
 * @date 2019-04-30 14:22
 */
public class ReservoirSampling {
    Random random;
    ListNode head;

    /** @param head The linked list's head.
    Note that the head is guaranteed to be not null, so it contains at least one node. */
    public ReservoirSampling(ListNode head) {
        this.random = new Random();
        this.head = head;
    }

    public int getRandom() {
        int count = 1;
        ListNode node = head;
        ListNode current = head.next;
        while (current != null) {
            if (random.nextInt(++count) == 0) {
                node = current;
            }
            current = current.next;
        }
        return node.val;
    }

    public int getRandom2() {
        int res = head.val;
        ListNode temp = head.next;
        for (int i = 1; temp != null; i++) {
            if (random.nextInt(i + 1) == 0) {
                res = temp.val;
            }
            temp = temp.next;
        }
        return res;
    }


    private static class ListNode {
       int val;
       ListNode next;
       ListNode(int x) { val = x; }
    }



    public static void main(String[] args) {
        int[] as = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        ListNode head = new ListNode(as[0]);
        ListNode next = new ListNode(as[1]);
        head.next = next;
        for (int i = 2; i < as.length; i ++) {
            ListNode node = new ListNode(as[i]);
            next.next = node;
            next = node;
        }
        Map<Integer, Integer> map = new HashMap<>(16);
        for (int i = 0; i < as.length; i ++) {
            map.put(as[i], 0);
        }
        ReservoirSampling reservoirSampling = new ReservoirSampling(head);
        int num = 20000;
        for (int i = 0; i < num; i++) {
            int n = reservoirSampling.getRandom();
            map.put(n, map.get(n) + 1);
        }
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
