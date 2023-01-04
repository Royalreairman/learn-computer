package com.completableFuturn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 合并计算thenCombine  获取两个线程的计算结果
 */
public class CompletableFutureDemo2 {
    //合并写
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> thenCombineResult = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in 1");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in 2");
            return 20;
        }), (x, y) -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in 3");
            return x + y;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in 4");
            return 30;
        }), (a, b) -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in 5");
            return a + b;
        });

        System.out.println("----主线程结束 ，END");
        System.out.println(thenCombineResult.get());
        //分开写
        CompletableFuture<Integer> thenCombineResult1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in 1");
            return 10;
        });
        CompletableFuture<Integer> thenCombineResult2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "----come in 1");
            return 10;
        });
        final CompletableFuture<Integer> result = thenCombineResult1.thenCombine(thenCombineResult2, (x, y) -> {
            return x + y;
        });
        System.out.println(result.get());
    }

}
