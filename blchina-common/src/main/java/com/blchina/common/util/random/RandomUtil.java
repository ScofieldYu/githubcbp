package com.blchina.common.util.random;

import java.util.Random;

public class RandomUtil {
    /**
     * 判断随机数，50%的概率
     */
    public static boolean isOne() {
        Random random = new Random();
        return random.nextInt(2) == 1;
    }

    /**
     *  0 - length 的随机数
     * @param length
     * @return
     */
    public static int random(int length) {
        return new Random().nextInt(length);
    }

    /**
     * 判断是奇数
     */
//    public static boolean isOdd() {
//        Random random = new Random();
//        return random.nextInt(2) == 1;
//    }

    /**
     * 判断是偶数
     */
//    public static boolean isEven() {
//        Random random = new Random();
//        return random.nextInt(10) % 2 == 0;
//    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Random().nextInt(2));
        }
    }


}
