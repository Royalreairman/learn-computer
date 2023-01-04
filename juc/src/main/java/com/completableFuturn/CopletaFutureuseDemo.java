package com.completableFuturn;

import java.util.concurrent.*;

/**
 * @author dubin
 * @create 2023-01-05 10:04
 */
public class CopletaFutureuseDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {

            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "---come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---1分钟后出结果:" + result);
                return result;
                //第一个参数是返回值，第二个异常信息
            },threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    System.out.println("计算完成，更新系统");
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("异常情况：" + e.getMessage() + "\t" + e.getCause());
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }
}
