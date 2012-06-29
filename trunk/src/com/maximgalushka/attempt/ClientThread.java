package com.maximgalushka.attempt;

import com.maximgalushka.attempt.commands.ConnectEvent;
import com.maximgalushka.attempt.commands.ReadEvent;
import com.maximgalushka.nio.nioserver.ChangeRequest;
import com.maximgalushka.nio.nioserver.ServerDataEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <p>Main thread which accepts client connections</p>
 *
 * @author Maxim Galushka
 * @since 27.06.12
 */
public class ClientThread extends NioThread {

    private static Log log = LogFactory.getLog(ClientThread.class);

    private final BlockingQueue<ServerDataEvent> bqueue = new ArrayBlockingQueue<ServerDataEvent>(100);

    protected ClientThread(InetAddress hostAddress, int port, NioThread colleague) throws IOException {
        super(hostAddress, port, colleague);
    }

    public ClientThread(InetAddress hostAddress, int port) throws IOException {
        super(hostAddress, port);
    }


    /**
     * just sends "CONNECT" command to colleague thread
     *
     * @param key
     */
    @Override
    public void connect(SelectionKey key) throws InterruptedException {
        this.colleague.push(new ConnectEvent());
    }

    /**
     * just sends "SEND" command to colleague thread
     *
     * @param socket
     * @param data
     */
    protected void send(SocketChannel socket, byte[] data) {
        log.debug(String.format("SEND: [%d]", socket.validOps()));

        synchronized (this.changeRequests) {
            // Indicate we want the interest ops set changed
            this.changeRequests.add(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));

            // And queue the data we want written
            synchronized (this.pendingData) {
                List<ByteBuffer> queue = this.pendingData.get(socket);
                if (queue == null) {
                    queue = new ArrayList<ByteBuffer>();
                    this.pendingData.put(socket, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }

        // Finally, wake up our selecting thread so it can make the required changes
        this.selector.wakeup();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                // Process any pending changes
                synchronized(this.changeRequests) {
                    for (ChangeRequest change : this.changeRequests) {
                        switch (change.type) {
                            case ChangeRequest.CHANGEOPS:
                                SelectionKey key = change.socket.keyFor(this.selector);
                                key.interestOps(change.ops);
                        }
                    }
                    this.changeRequests.clear();
                }

                // Wait for an event one of the registered channels
                this.selector.select();

                // Iterate over the set of keys for which events are available
                Iterator selectedKeys = this.selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    // Check what event is available and deal with it
                    if (key.isAcceptable()) {
                        this.accept(key);
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

    @Override
    public ByteBuffer read(SelectionKey key) throws IOException, InterruptedException {
        ByteBuffer buffer = super.read(key);

        colleague.push(new ReadEvent(buffer));

        return buffer;
    }
}
