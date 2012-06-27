package com.maximgalushka.attempt;

import com.maximgalushka.nio.nioserver.ChangeRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

/**
 * <p>Forward thread which forwards content to remote server and back</p>
 *
 * @author Maxim Galushka
 * @since 27.06.12
 */
public class NioForwardThread extends NioThread {

    public NioForwardThread(InetAddress hostAddress, int port, NioThread main) throws IOException {
        super(hostAddress, port, main);
    }

    protected NioForwardThread(InetAddress hostAddress, int port) throws IOException {
        super(hostAddress, port);
    }

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
                        this.finishConnection(key);
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
