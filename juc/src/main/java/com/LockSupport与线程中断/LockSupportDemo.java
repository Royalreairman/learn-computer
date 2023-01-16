package com.LockSupport与线程中断;


import java.util.concurrent.TimeUnit;

//等待唤醒机制

/**
 * wait方法和notify方法，两个都去掉同步代码块
 * 将notify放在wait方法前面
 * 程序无法执行，无法唤醒
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        Object objectLock = new Object();

        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t-----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t-----被唤醒");
            }

        }, "t1").start();
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronized (objectLock) {
             objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t-----发出通知");
            }

        }, "t2").start();

    }
}
