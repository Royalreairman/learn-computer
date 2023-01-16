package com.jmm;


import java.text.MessageFormat;

public class Demo {
    /**
     * JMM(Java内存模型Java Memory Model,简称JMM)本身是一种抽象的概念并不真实存在它仅仅描述的是一组约定或规范，通过这组
     * 规范定义了程序中（尤其是多线程）各个变量的读写访问方式并决定一个线程对共享变量的写入何时以及如何变成对另一个线程可见，关
     * 键技术点都是围绕多线程的原子性、可见性和有序性展开的。
     * 原则：
     * JMM的关键技术点都是围绕多线程的原子性、可见性和有序性展开的
     * 能干嘛？
     * 1通过JMM来实现线程和主内存之间的抽象关系。
     * 2屏蔽各个硬件平台和操作系统的内存访问差异以实现让Java程序在各种平台下都能达到一致的内存访问效果。
     */
    public static void main(String[] args) {
        String url = "我叫%s,今年%s岁。";
        String name = "小明";
        String age = "28";
        url = String.format(url, name, age);
        System.out.println(url);

        String url02 = "我叫{0},今年{1}岁。";

        name = "小明";
        age = "28";
        url02 = MessageFormat.format(url02, name, age);
        System.out.println(url02);

    }
}

