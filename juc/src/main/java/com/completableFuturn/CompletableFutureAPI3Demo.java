package com.completableFuturn;

import java.util.concurrent.CompletableFuture;

/**
 * @author dubin
 * @create 2023-01-05 17:06
 */
public class CompletableFutureAPI3Demo {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 2;
        }).thenApply(f -> {
            return f + 3;
            //接收 任务的处理结果 ，并消费处理 ，无返回结果
        }).thenAccept(System.out::println);
        /**
         * thenRun  方法不需要 依赖 上一个线程的结果   完成独立
         * thenAccept  上一个线程的结果与下一个线程的结果有关联  无返回值
         * thenApply   上一个线程的结果与下一个线程的结果有关联 有返回值
         */
        System.out.println(CompletableFuture.supplyAsync(() -> "result").thenRun(()->{}).join());
    }
}
