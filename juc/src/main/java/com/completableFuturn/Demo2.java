package com.completableFuturn;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//案列  completableFuture 实战 案列

/**
 * 通过传统的执行任务 会一个一个执行速度很慢，但是我们把每一个任务单独开一个线程会大大提升程序的性能 ，我这个案列提升额性能
 */
public class Demo2 {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream().map(
                netMall ->
                        String.format(productName + "in %s price is %.2f this ",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
         long startTime = System.currentTimeMillis();
        final List<String> list1 = getPrice(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("-----costTime" + (endTime - startTime) + "毫秒");
        startTime = System.currentTimeMillis();
        final List<String> list2 = getPriceCompletableFuture(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
          endTime = System.currentTimeMillis();
        System.out.println("-----costTime" + (endTime - startTime) + "毫秒");


    }
    public static List<String> getPriceCompletableFuture(List<NetMall> list,String productName){
        return list.stream().map(netMall ->CompletableFuture.supplyAsync(()->
            String.format(productName + "in %s price is %.2f this ",
                    netMall.getNetMallName(),
                    netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(s->s.join())
                .collect(Collectors.toList());

    }
}

@Getter
class NetMall {
    private String netMallName; //电商网站

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0); //随机生成一个价格
    }

}
