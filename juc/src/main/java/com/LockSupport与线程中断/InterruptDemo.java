package com.LockSupport与线程中断;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 一个线程不应该由其他线程来强制中断或停止，而是应该由线程自己自行停止，自己来决定自己的命运。
 * 所以，Thread.stop,Thread.suspend,Thread.resume都已经被废弃了。
 * 其次
 * 在Java中没有办法立即停止一条线程，然而停止线程却显得尤为重要，如取消一个耗时操作。
 * 因此，Java提供了一种用于停止线程的协商机制一一中断，也即中断标识协商机制。
 * 中断只是一种协作协商机制，Java没有给中断增加任何语法，中断的过程完全需要程序员自己实现。
 * 若要中断一个线程，你需要手动调用该线程的interrupt方法，该方法也仅仅是将线程对象的中断标识设成true;
 * 接着你需要自己写代码不断地检测当前线程的标识位，如果为tue,表示别的线程请求这条线程中断，
 * 此时究竞该做什么需要你自己写代码实现。
 * 每个线程对象中都有一个中断标识位，用于表示线程是否被中断；该标识位为tue表示中断，为false表示未中断；
 * 通过调用线程对象的interrupt方法将该线程的标识位设为true;可以在别的线程中调用，也可以在自己的线程中调用。
 */

public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);  //原子

    public static void main(String[] args) {
        /**
         * 具体来说，当对一个线程，调用interrupt)时：
         * ①如果线程处于证常活动状态，那么会将该线程的中断标志设置为tue,仅此而已。
         * 被设置中断标志的线程将继续正常运行，不受影响。
         * 所以，interrupt(()并不能真正的中断线程，需要被调用的线程自己进行配合才行。
         * ②如果线程处于被阻塞状态（例如处于sleep,wait,join等状态），在别的线程中调用当前线程对象的interrupt方法，
         * 那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。
         */

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t isInterrupted被修改为"+Thread.currentThread().isInterrupted()+",程序停止");
                    break;
                }
                System.out.println("----hello isInterrupted API");
            }
        }, "t1");
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            t1.interrupt();
            System.out.println(Thread.currentThread().isInterrupted());
            t1.interrupt();
            System.out.println(Thread.currentThread().isInterrupted());
        }, "t2").start();
    }

    private static void atomic() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean被修改为true,程序停止");
                    break;
                }
                System.out.println("----hello volatile");
            }
        }, "t1").start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    /**
     * 利用  volatile实现中断程序
     */
    private static void volatileTest() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true,程序停止");
                    break;
                }
                System.out.println("----hello volatile");
            }
        }, "t1").start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
