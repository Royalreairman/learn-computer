package com.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AIO服务器
 */
public class AIOServer {
    private final  int port;

    public static void main(String[] args) {
        int port=8081;
        new AIOServer(port);
    }
    public  AIOServer (int port) {
        this.port=port;
        listen();
    }

    private void listen() {
        try {
            //
           ExecutorService executorService = Executors.newCachedThreadPool();
            AsynchronousChannelGroup threadGroup =
                    AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            final AsynchronousServerSocketChannel server =
                    AsynchronousServerSocketChannel.open(threadGroup);
            server.bind(new InetSocketAddress(port));
            System.out.println("服务已启动，监听端口"+port);
            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("I/O操作失败:"+exc);
                }

                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("I/O操作成功，开始获取数据");
                    try {
                        buffer.clear();//清除此缓冲区。 位置设置为零，限制设置为容量，标记被丢弃。
                        result.read(buffer).get();
                        buffer.flip();//翻转这个缓冲区。 该限制设置为当前位置，然后将该位置设置为零。 如果标记被定义，则它被丢弃。
                        result.write(buffer);
                        buffer.flip();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    } finally {
                        try {
                            result.close();
                            server.accept(null, this);
                        } catch (IOException e) {
                            System.out.println(e.toString());
                        }

                    }
                    System.out.println("操作完成");
                }

            });
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
