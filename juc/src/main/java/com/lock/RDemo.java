package com.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁 又名递归锁
 * 是指在同一线程在外层  方法获取锁的时候，在进入 线程的内层方法会自动获取锁 （前提，锁对象得是同一个对象 ），不会因为之前已经获取过还没释放而阻塞。
 * <p>
 * 可： 可以
 * 入：再次
 * 锁：同步锁
 * 进入： 进入同步锁
 * <p>
 * 在一个synchronize 修饰的方法或代码块的内部调用本类的其他synchronize修饰的方法或代码块时，是永远可以得到锁
 *
 * 每个锁对象拥有一个锁计数器和一个指向持有该锁的线程的指针。
 * 当执行monitorenter时，如果目标锁对象的计数器为零，那么说明它没有被其他线程所持有，Java虚拟机会将该锁对象的持有线程设置
 * 为当前线程，并且将其计数器加1。
 * 在目标锁对象的计数器不为零的情况下，如果锁对象的持有线程是当前线程，那么JVa虚拟机可以将其计数器加1，否则需要等待，
 * 直至持有线程释放该锁。
 * I
 * 当执行monitorexith时，Java虚拟机则需将锁对象的计数器减1。计数器为零代表锁已被释放。
 *
 *
 */
public class RDemo {

    public  synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + "\t----come m1");
        m2();
        System.out.println(Thread.currentThread().getName() + "\t---- end m1" );
    }
    public  synchronized void m2(){
        System.out.println(Thread.currentThread().getName() + "\t----come m2");
        m3();
    }
    public  synchronized void m3(){
        System.out.println(Thread.currentThread().getName() + "\t----come m3");
    }

    static Lock lock = new ReentrantLock();


    public static void main(String[] args) {


    }

    private static void reEnterM2() {
        final RDemo rDemo = new RDemo();
        new Thread(()->{
            rDemo.m1();
            lock.lock();  //获取锁
            lock.unlock();  //释放锁
        },"t1").start();
    }

    private static void reEnterM1() {
        Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t----外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t----中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t----内层调用");
                    }
                }
            }
        }, "t1").start();
    }
}
