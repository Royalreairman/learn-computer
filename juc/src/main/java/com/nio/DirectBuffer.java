package com.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//直接缓冲区
public class DirectBuffer {
    public static void main(String[] args) throws Exception {
        //首先从磁盘读取之前写出的文件内容
        String infile = "E:\\笔记\\learn-computer\\test.txt";
        FileInputStream fin = new FileInputStream(infile);
        FileChannel fcin = fin.getChannel();
        //把读取的文件写人一个新的文件
        String outfile = String.format("E:\\笔记\\learn-computer\\testcopy.txt");
        FileOutputStream fout = new FileOutputStream(outfile);
        FileChannel fcout = fout.getChannel();
        //使用allocateDirect,而不是allocate
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            buffer.clear();
            int r = fcin.read(buffer);
            if (r == 1) {
                break;
            }
            buffer.flip();
            fcout.write(buffer);
        }
        fin.close();
        fout.close();
    }
}
