package com.maximgalushka.attempt;

import com.maximgalushka.nio.nioserver.ChangeRequest;
import com.maximgalushka.nio.nioserver.NioServer;
import com.maximgalushka.nio.nioserver.ServerDataEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <p>Forward thread which forwards content to remote server and back</p>
 *
 * This keeps inside the queue of pending event and constantly monitor them and sent
 * notifications to the selection thread.
 *
 * @author Maxim Galushka
 * @since 27.06.12
 */
public class ForwardThread extends NioThread {

    private static Log log = LogFactory.getLog(ForwardThread.class);

    private final BlockingQueue<ServerDataEvent> bqueue = new ArrayBlockingQueue<ServerDataEvent>(100);

    public ForwardThread(InetAddress hostAddress, int port, NioThread main) throws IOException {
        super(hostAddress, port, main);
    }

    protected ForwardThread(InetAddress hostAddress, int port) throws IOException {
        super(hostAddress, port);
    }

    public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];

        // TODO: weak references here to make sure ...
        System.arraycopy(data, 0, dataCopy, 0, count);
        try {
            bqueue.put(new ServerDataEvent(server, socket, dataCopy));
            log.debug(String.format("PUT seccessfully: [%s]", socket.validOps()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void run() {
//        //noinspection InfiniteLoopStatement
//        while(true) {
//            try {
//                // Wait for data to become available
//                ServerDataEvent dataEvent = bqueue.take();
//                log.debug(String.format("TAKE seccessfully: [%s]", dataEvent.socket.validOps()));
//                if(dataEvent != null){
//                    // TODO: do forwarding logic - send to client forwarded content
//                    // Return to sender
//                    dataEvent.server.send(dataEvent.socket, dataEvent.data);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                // Process any pending changes
                synchronized (this.changeRequests) {
                   for(ChangeRequest change : this.changeRequests) {
                        switch (change.type) {
                            case ChangeRequest.CHANGEOPS:
                                SelectionKey key = change.socket.keyFor(this.selector);
                                key.interestOps(change.ops);
                                break;
                            case ChangeRequest.REGISTER:
                                change.socket.register(this.selector, change.ops);
                                break;
                        }
                    }
                    this.changeRequests.clear();
                }

                // Wait for an event one of the registered channels
                this.selector.select();

                // Iterate over the set of keys for which events are available
                Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    // Check what event is available and deal with it
                    if (key.isConnectable()) {
                        this.close(key);
                    } else if (key.isReadable()) {
                        this.read(key);
                    } else if (key.isWritable()) {
                        this.write(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
