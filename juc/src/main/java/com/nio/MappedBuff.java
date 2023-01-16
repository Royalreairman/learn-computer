package com.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


/**
 * I/O映射缓冲区
 */
public class MappedBuff {
    private static final int size = 1024;
    private static final int start = 0;
    private static final int port = 8091;


    public static void main(String[] args) throws Exception {
        /**
         * 该类的实例支持读取和写入随机访问文件。 随机访问文件的行为类似于存储在文件系统中的大量字节。
         * 有一种游标，或索引到隐含的数组，称为文件指针 ; 输入操作读取从文件指针开始的字节，
         * 并使文件指针超过读取的字节。 如果在读/写模式下创建随机访问文件，则输出操作也可用;
         * 输出操作从文件指针开始写入字节，并将文件指针提前到写入的字节。
         * 写入隐式数组的当前端的输出操作会导致扩展数组。
         * 文件指针可以通过读取getFilePointer方法和由设置seek方法。
         * 在这个类中的所有读取例程通常都是如果在读取所需的字节数之前到达文件结尾，则抛出一个EOFException
         * （这是一种IOException ）。 如果任何字节由于除文件末尾之外的任何原因而无法读取，
         * 则抛出IOException以外的EOFException 。 特别地，如果流已经被关闭，则可以抛出IOException 。
         */
        RandomAccessFile raf = new RandomAccessFile("E:\\笔记\\learn-computer\\test.txt", "rw");
        FileChannel fc = raf.getChannel();
        //把缓冲区跟文件系统进行一个映射关系
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
        mbb.put(0, (byte) 97);
        mbb.put(1023, (byte) 122);
        raf.close();

    }

    /**
     * 注册事件
     *
     * @return
     * @throws Exception
     */
    private Selector getSelector() throws Exception {
        //创建Selector对象
        Selector sel = Selector.open();
        //创建可选择器通道，并配置为非阻塞模式
        ServerSocketChannel sever = ServerSocketChannel.open();
        sever.configureBlocking(false);
        //绑定通道到指定端口
        ServerSocket socket = sever.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        socket.bind(address);

        //向Selector注册感兴趣的事件
        sever.register(sel, SelectionKey.OP_ACCEPT);
        return sel;
    }

    /**
     * 开始监听
     */
    public void listen() throws IOException {
        System.out.println("listen on" + port);
        Selector selector = Selector.open();
        while (true) {
            //该通道会阻塞，直到至少有一个事件发生
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = (SelectionKey) iter.next();
                iter.remove();
                process(key);
            }
        }


    }

    private void process(SelectionKey key) throws IOException {
        Selector selector = Selector.open();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //接收请求
        if (key.isAcceptable()) {
            ServerSocketChannel serve = (ServerSocketChannel) key.channel();
            SocketChannel channel = serve.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        }
        //读数据
        else if (key.isReadable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            int len = channel.read(buffer);
            if (len > 0) {
                buffer.flip();
                String content = new String(buffer.array(), 0, len);
                SelectionKey skey = channel.register(selector, SelectionKey.OP_WRITE);
                skey.attach(content);
            } else {
                channel.close();
            }
            buffer.clear();
        }
        //写事件
        else if (key.isWritable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            String content = (String) key.attach(channel);
            ByteBuffer block = ByteBuffer.wrap(("输出内容:" + content).getBytes());
            if (block != null) {
                channel.write(block);
            } else {
                channel.close();
            }

        }
    }

}
