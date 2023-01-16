package com.nio;


import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IntBufferDemo1 {
  public static void main(String[] args) throws Exception {
      //这里用的文件I/O处理
      final FileInputStream fin = new FileInputStream("../test.txt");
      //创建文件的操作管道
      final FileChannel fc = fin.getChannel();
      //分配一个10个大小 的缓冲区，其实就是分配一个10个大小的Byte数组
      final ByteBuffer buffer = ByteBuffer.allocate(10);
      outPut("初始化",buffer);
      //先读一下
      fc.read(buffer);
      outPut("调用flip()",buffer);
      //判断有没有可读数据
      while (buffer.remaining() > 0) {
          final byte b = buffer.get();
      }

      outPut("调用get()",buffer);
      //可以理解为解锁
      buffer.clear();
      outPut("调用clear()",buffer);
      //最后把管道关闭
      fin.close();


  }
  public static void outPut(String step, ByteBuffer buffer) {
      System.out.println(step+":");
      //容量，数量大小
      System.out.println("capacity:"+buffer.capacity());
      //当前操作数据所在的位置，也可以叫作游标
      System.out.println("position"+buffer.position());
      //锁定值，flip ，数组操作范围索引只能在position-limit之间
      System.out.println("limit:"+buffer.limit());
      System.out.println();
  }
}
