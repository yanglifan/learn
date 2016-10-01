package yanglifan.learn.core.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * When the select thread is interrupted, no exception will thrown but the blocked select operation will return.
 */
public class InterruptOnSelectDemo {
    @Test
    public void demo() throws Exception {
        Thread serverThread = new Thread(() -> {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(10000));
                serverSocketChannel.configureBlocking(false);
                Selector selector = Selector.open();
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                while (!Thread.currentThread().isInterrupted()) {
                    int num = selector.select();
                    println("Selected num " + num);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        serverThread.start();

        Thread.sleep(5000);

        serverThread.interrupt();

        Thread.sleep(1000);

        println("End");
    }

    private void println(String x) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        System.out.println(simpleDateFormat.format(new Date()) + " -> " + x);
    }
}
