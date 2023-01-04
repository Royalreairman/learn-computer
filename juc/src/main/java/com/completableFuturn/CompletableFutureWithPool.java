package com.completableFuturn;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 1.没有传入 自定义线程 ，都默认 线程池 ForkJoInPool
 * 2.传入 一个自定义的线程池
 * 如果你执行第一个任务的时候 ，传入一个自定义线程池
 * 调用 thenRun 方法执行第二个任务时，则第二个任务和第一个任务 是共用一个线程池
 * 调用 thenRunAsync 执行第二个任务时，则第一个 任务使用的是你自己传入的线程池 ，第二个任务使用的是ForkJOin
 * 3.备注
 * 有可能处理太快，系统优化切换原则 ，直接使用main 线程处理
 * 其它如： thenAccept和 thenAcceptAsync,thenApply 和 thenApplyASync等 他们之间的区别也是同理
 */

public class CompletableFutureWithPool {
    public static void main(String[] args) {
        final ExecutorService threadPool = Executors.newFixedThreadPool(5);
        
        try {
            final CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1号任务" + "\t" + Thread.currentThread().getName());
                return "abcd";

            }).thenRunAsync(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("3号任务" + "\t" + Thread.currentThread().getName());

            }).thenRun(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("4号任务" + "\t" + Thread.currentThread().getName());

            });
            System.out.println(completableFuture.get(2L,TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
