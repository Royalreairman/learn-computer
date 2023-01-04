package com.lock;

/**
 * @author dubin
 * @create 2023-01-06 15:31
 */
/*死锁 是指 两个或两个以上的线程 互相争夺资源导致的
 原因：   系统资源不足
          进程运行推进顺序不合适
          资源 分配不当

          java 命令
          jps -l  : 获取到每一个类运行的进程
          jstack 进程号 ： 查询死锁

            图形化： jconsole 软件
 */
/*
指针指向monitor对象（也称为管程或监视器锁）的起始地址。每个对象都存在着一个monitor与之关联，当一个monitor被某个线程持有后，它便
处于锁定状态。在Java虚拟机(HotSpot)中，monitor是由ObjectMonitor实现的，其主要数据结构如下（位于HotSpot/虚拟机源码
ObjectMonitor.hpp文件，C++实现的)
runoob.com/manual/jdk11api/java.base/java/lang/Thread.html#isInterruptedo
 */

public class 死锁 {

    public static void main(String[] args) {
        final Object object = new Object();
    }
}
