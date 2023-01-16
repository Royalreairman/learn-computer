package com.LockSupport与线程中断;

/**
 * 静态方法，Thread.interrupted():
 * 判断线程是否被中断并清除当前中断状态。
 * 这个方法做了两件事：
 * 1返回当前线程的中断状态，测试当前线程是否已被中断
 * 2将当前线程的申断状态清零并重新设为false,清除线程的中断状态
 * 此方法有点不好理解，如果连续两次调用此方法，则第二次调用将返回false,因为连续调用两次的结果可能不一样
 */
public class InterruptDemo4 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "" + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "" + "\t" + Thread.interrupted());
        System.out.println("-----1");
        Thread.currentThread().interrupt(); //中断标准位设置为true
        System.out.println("-----2");
        System.out.println(Thread.currentThread().getName() + "" + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "" + "\t" + Thread.interrupted());


    }
}
