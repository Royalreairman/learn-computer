package com.completableFuturn;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


//api文档案列

/**
 * get方法 可以设置 等待时间 ，但是如果 在等待时间结束后没有获取到结果，就会抛TimeoutException get 方法有阻塞的特点
 */
public class CompletableFutureAPIDemo {
    public static void main(String[] args) throws Exception {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(completableFuture.getNow("XXX"));  //立即获取到结果 是对get方法的增强 不阻塞
        System.out.println(completableFuture.complete("值"+"\t"+completableFuture.join()));  //类似getNow 为boolean类型

    }
}
