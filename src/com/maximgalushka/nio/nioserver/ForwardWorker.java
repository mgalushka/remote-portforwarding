package com.maximgalushka.nio.nioserver;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ForwardWorker implements Runnable {

    private final BlockingQueue<ServerDataEvent> bqueue = new ArrayBlockingQueue<ServerDataEvent>(100);

    public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];

        // TODO: weak references here to make sure ...
        System.arraycopy(data, 0, dataCopy, 0, count);
        try {
            bqueue.put(new ServerDataEvent(server, socket, dataCopy));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while(true) {
            try {
                // Wait for data to become available
                ServerDataEvent dataEvent = bqueue.take();
                if(dataEvent != null){
                    // TODO: do forwarding logic - send to client forwarded content
                    // Return to sender
                    dataEvent.server.send(dataEvent.socket, dataEvent.data);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
