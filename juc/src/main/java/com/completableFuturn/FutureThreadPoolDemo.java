package com.completableFuturn;

import java.util.concurrent.*;

/**
 * @author dubin
 * @create 2023-01-04 16:54
 */

/**
 * Future 接口可以交由线程池处理 提交效率
 * 他的 get方法建议放在程序的最后，get会导致程序堵塞， 补救办法可以设置一个休眠时间  futureTask1.get(3,TimeUnit.SECONDS);
 * isDone()轮询的方式会消耗无谓的CPU资源，而且也不见得能及时地计算
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            return "task1 over";
        });
        final Thread t1 = new Thread(futureTask1, "t1");
        t1.start();
        System.out.println("忙其他任务了"+Thread.currentThread().getName());
       while (true){
           if(futureTask1.isDone()){
               System.out.println(futureTask1.get());
               break;
           }else {
               Thread.sleep(5000);
               System.out.println("正在运行别急");
           }
       }


    }
}
