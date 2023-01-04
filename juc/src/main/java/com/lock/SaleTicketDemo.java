package com.lock;

import java.util.concurrent.locks.ReentrantLock;

class Ticket {  //资源类 ，模拟 3个售票员卖50张票
    private int number = 50;
    ReentrantLock lock = new ReentrantLock();
    //卖票方法

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第：\t" + (number--) + "\t 还剩下：" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}


/**
 * 公平锁   ReentrantLock lock = new ReentrantLock(true);   公平锁 就是平均分配
 * 非公平锁      ReentrantLock lock = new ReentrantLock();  非公平锁 就是随机分配     非公平锁可以充分利用CPU的时间片 ，尽量减少CPU空闲时间，
 * 当一个线程 请求 锁获取同步状态 ，然后释放 同步状态 ，所以刚释放锁的线程在此刻再次获取同步的概率就变得非常大 ，所以就减少了线程的开销。
 */
public class SaleTicketDemo {
    public static void main(String[] args) {
        final Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) ticket.sale();
        }, "a").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) ticket.sale();
        }, "b").start();
        new Thread(() -> {
            for (int i = 0; i < 55; i++) ticket.sale();
        }, "c").start();
    }
}
