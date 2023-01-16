package com.aio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;


/**
 * AIO客户端
 */
public class AIOClient {

    private final AsynchronousSocketChannel client;  //用于面向流的连接插座的异步通道。

    public AIOClient() throws Exception {
        /**
         *
         */
        client = AsynchronousSocketChannel.open();
    }

    public void connect(String host, int port) throws Exception {
        /**
         * connect
         * 参数
         * remote - 要连接此通道的远程地址
         * attachment - 要附加到I / O操作的对象; 可以是null
         * handler - 消耗结果的处理程序
         * 异常
         * UnresolvedAddressException - 如果给定的远程地址未完全解析
         * UnsupportedAddressTypeException - 如果不支持给定的远程地址的类型
         * AlreadyConnectedException - 如果此频道已连接
         * ConnectionPendingException - 如果此通道上已经有连接操作
         * ShutdownChannelGroupException - 如果通道组已经终止
         * SecurityException - 如果已安装安全管理器，并且不允许访问给定的远程端点
         */
        client.connect(new InetSocketAddress(host, port), null,
                new CompletionHandler<Void, Void>() { //用于消除异步I / O操作结果的处理程序。
                    @Override
                    public void completed(Void result, Void attachment) {
                        try {
                            client.write(ByteBuffer.wrap("这是一条测试数据".getBytes())).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                        exc.printStackTrace();
                    }
                });
        final ByteBuffer bb = ByteBuffer.allocate(1024); //分配一个新的字节缓冲区。
        client.read(bb, null, new CompletionHandler<Integer, Object>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        System.out.println("I/O操作完成" + result);
                        System.out.println("获取反馈结果" + new String(bb.array()).trim());
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        exc.printStackTrace();
                    }
                }
        );
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        new AIOClient().connect("localhost", 8081);
    }


}