package com.LockSupport与线程中断;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 正常+无锁块都支持
 * 之前错误的先唤醒后等待，LockSupport照样支持
 * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。
 * LockSupport是一个线程阻塞工具类，所有的方法都是静态方法，可以让线程在任意位置阻塞，阻塞之后也有对应的唤醒方法。归根结
 * 底，LockSupport调用的Unsafe中的native代码。
 * LockSupport提供park()和unpark()方法实现阻塞线程和解除线程阻塞的过程
 * LockSupport7和每个使用它的线程都有一个许可(permit))关联。
 * 每个线程都有一个相关的permit,permit最多只有一个，重复调用unpark也不会积累凭证。
 * 形象的理解
 * 线程阻塞需要消耗凭证(permit)),这个凭证最多只有1个。
 * 当调用par水方法时
 * *如果有凭证，则会直接消耗掉这个凭证然后正常退出：
 * *如果无凭证，就必须阻塞等待凭证可用；
 * 而unpark则相反，它会增加一个凭证，但凭证最多只能有1个，累加无效。
 */
public class LockSupportDemo2 {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t-----come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t-----被唤醒");

        }, "t1");
        t1.start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t----发出通知");

        }, "t2").start();
    }
}
