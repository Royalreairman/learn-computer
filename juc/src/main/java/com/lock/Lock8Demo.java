package com.lock;

import java.util.concurrent.TimeUnit;

class phone {
    public synchronized void sendEmail() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("----sendSMS");
    }
    public  void hello() {
        System.out.println("----hello");

    }

}

/**
 * 笔记总结：
 * 一个对象里面如果有多个synchronized 方法，某一个时刻内 只要一个线程去调用其中的一个synchronized方法了 ，
 * 其他的线程 只能等待 换句话说，某一个时刻内，只能有唯一的一个线程 去访问  这些 synchronized 方法
 * 锁 的是当前对象this ,被锁定后， 其他的线程 都不能进入当前对象的其他的synchronize方法
 * 1-2
 * 一个对象里面如果有多个synchronized.方法，某一个时刻内，只要一个线程去调用其中的一个synchronized.方法了，
 * 其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一的一个线程去访间这些synchronized.方法
 * 锁的是当前对象this,被锁定后，其它的线程都不能进入到当前对象的其它synchronized.方法
 * 3-4
 * 加个普通方法后发现和同步锁无关
 *换成两个对象后，不是同一把锁了，情况立刻变化。
 * 5-6都换成静态同步方法后，情况又变化
 * 三种synchronized锁的内容有一些差别：
 * 时于普通同步方法，锁的是当前实例对象，通常指this,具体的一部部手机，所有的普通同步方法用的都是同一把锁一>实例对象本身，
 * 付于静态同步方法，锁的是当前类的Class对象，如Phone.class唯一的一个模板
 * 对于同步方法块，锁的是synchronized括号内对象
 * 7-8
 * 当一个线程试图访问同步代码时它首先必须得到锁，正常退出或抛出异常时必须释放锁。
 * 所有的普通同步方法用的都是同一把锁一实例对象本身，就是new出来的具体实例对象本身，本类this
 * 也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁。
 * 所有的静态同步方法用的也是同一把锁一类对象本身，就是我们说过的唯一模板class
 * 具体实例对象this和唯一模板class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的
 * 但是一且一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后，才能获取锁。
 *
 */

// 多线程 锁的理解
public class Lock8Demo {
    /*
    1.标准访问 ab线程 ，请问 是先打印 邮件还是短信
    答案：  ----sendEmail
            ----sendSMS
    public static void main(String[] args) {
        final phone phone = new phone();
        new Thread(()->{
            phone.sendEmail();
        },"a").start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            phone.sendSMS();
        },"b").start();
    }
     */
    /*
     2.sendEmail 方法中加入暂停3秒钟 ，请问先打印邮件还是短信
      答案：  ----sendEmail
----sendSMS

    public static void main(String[] args) {
        final phone phone = new phone();
        new Thread(()->{
            phone.sendEmail();
            try {
                TimeUnit.MILLISECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"a").start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            phone.sendSMS();
        },"b").start();
    }

     */
    /*
     3.添加一个 hello方法 ，请问是先打印 邮件还是 hello
       答案：  ----hello
            ----sendEmail

    public static void main(String[] args) {
        final phone phone = new phone();
        new Thread(()->{
            phone.sendEmail();

        },"a").start();

        new Thread(()->{
            phone.hello();
        },"b").start();
    }

     */
}

