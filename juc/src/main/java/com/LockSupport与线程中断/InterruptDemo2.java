package com.LockSupport与线程中断;

import java.util.concurrent.TimeUnit;

/**
 * @author dubin
 * @create 2023-01-06 17:55
 */
public class InterruptDemo2 {
    public static void main(String[] args) {
        //实例方法Interrupt()仅仅设置线程的中断状态为true 不会停止线程
        final Thread t1 = new Thread(()->{
            for (int i = 0; i <=300 ; i++) {
                System.out.println("----:"+i);

            }
            System.out.println("t1线程默认的中断标识02："+Thread.currentThread().isInterrupted());
        },"t1");
        t1.start();
        System.out.println("t1线程默认的中断标识："+t1.isInterrupted());
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt(); //true
        System.out.println("t1线程默认的中断标识01："+t1.isInterrupted());
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1线程默认的中断标识03："+t1.isInterrupted());
    }
}
