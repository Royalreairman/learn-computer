package com.completableFuturn;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * thenApply 如果中途有异常会结束任务
 * handle 无论是否有异常 程序都会继续往下继续执行
 */
public class CompletableFutureAPI2Demo {
    /*
    public static void main(String[] args) {
        final ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
            //交由线程池管理
        },threadPool).thenApply(f->{
            System.out.println("222");
            return f+2;
            //获取到上一步的结果并且使用
        }).thenApply(f->{
            System.out.println("333");
            return f+3;
            //判断是否有结果
        }).whenComplete((v,e)->{
            if(e==null){
                System.out.println("---计算结果:"+v);
            }
        }).exceptionally(e->{
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });
        //守护线程一旦结束，其他线程就不会运行了
        System.out.println(Thread.currentThread().getName()+"----主线程去忙其他任务了");
        threadPool.shutdown();
    }

     */

    public static void main(String[] args) {
        final ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
            //交由线程池管理
        },threadPool).handle((f,e)->{
            System.out.println("222");
            return f+2;
            //获取到上一步的结果并且使用
        }).handle((f,e)->{
            System.out.println("333");
            return f+3;
            //判断是否有结果
        }).whenComplete((v,e)->{
            if(e==null){
                System.out.println("---计算结果:"+v);
            }
        }).exceptionally(e->{
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });
        //守护线程一旦结束，其他线程就不会运行了
        System.out.println(Thread.currentThread().getName()+"----主线程去忙其他任务了");
        threadPool.shutdown();
    }


}
