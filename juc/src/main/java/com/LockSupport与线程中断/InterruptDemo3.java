package com.LockSupport与线程中断;

import java.util.concurrent.TimeUnit;


public class InterruptDemo3 {
    public static void main(String[] args) {
        final Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t" +
                            "中断标志位：" + Thread.currentThread().isInterrupted() + "程序停止");
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    /**
                     * 为什么需要在异常处，再处理调用一次
                     * 1.中断标志位默认是false
                     * t2---t1发出了中断协商 ，t2调用t1 interrupt（），中断标准位true
                     * 中断标志位 true ，正常情况，程序停止 ，
                     * 中断 标志位 true ，异常情况 interruptException 将会把中断状态清楚，
                     * 并且 将会收到 interruptedException ，中断标准位false
                     *
                     * 在catch 快中，需要再次给中断标志位设置为true ,2次才ok
                     *
                     */
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("hello");
            }
        }, "t1");
        t1.start();
        //程序暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> t1.interrupt(), "t2").start();
    }
}
