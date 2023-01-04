package com.completableFuturn;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//异步多线程 任务执行且返回有结果  三个特点：多线程 /有返回 /异步
//https://www.matools.com/api/java8         api文档地址
public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new MyThread());
        final Thread t1 = new Thread(futureTask, "t1");
        t1.start();
        /**
         * 获取到 异步线程， get方法会堵塞
         */
        System.out.println(futureTask.get());
    }
}



class MyThread implements Callable<String>{
    public String call() throws Exception {
        System.out.println("----come in call()");
        return "hello callable";
    }
}