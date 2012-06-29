package com.maximgalushka.attempt;

import com.maximgalushka.nio.nioserver.ChangeRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

/**
 * <p>Main thread which accepts client connections</p>
 *
 * @author Maxim Galushka
 * @since 27.06.12
 */
public class NioClientThread extends NioThread {

    protected NioClientThread(InetAddress hostAddress, int port, NioThread colleague) throws IOException {
        super(hostAddress, port, colleague);
    }

    public NioClientThread(InetAddress hostAddress, int port) throws IOException {
        super(hostAddress, port);
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
}