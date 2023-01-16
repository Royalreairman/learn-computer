package com.LockSupport与线程中断;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程先要获得并特有锁，必须仕锁块(synchronized.或ock)中
 * 必须要先等待后唤醒，线程才能够被唤醒
 */
public class LockSupportDemo1 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t-----come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t-----被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t----发出通知");
            } finally {
                lock.unlock();
            }

        }, "t2").start();
    }
}
