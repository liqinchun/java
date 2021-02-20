package com.example.java;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 二进制运算
 *
 * 原码： +1 = -(-1) 将减法当加法计算
 * 反码： 反码相加 解决原码+负数问题
 * 补码:  0
 */
public class BinaryClass {


    public static void main(String[] args) {

        sum();
    }

    public static void sum() {
        Map map = new HashMap<>();
        int a = -10;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(10&-10);
        System.out.println(Integer.toBinaryString(a).length());
        System.out.println(Integer.toBinaryString(a>>1));
        System.out.println(Integer.toBinaryString(a>>>1));
        System.out.println(Integer.toBinaryString(a>>>1));
        System.out.println(Integer.toBinaryString(a<<1));
    }
}
