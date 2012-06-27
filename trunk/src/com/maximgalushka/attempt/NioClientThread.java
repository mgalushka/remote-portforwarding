package com.maximgalushka.attempt;

import java.io.IOException;
import java.net.InetAddress;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 27.06.12
 */
public class NioClientThread extends NioThread {

    public NioClientThread(InetAddress hostAddress, int port, NioThread colleague) throws IOException {
        super(hostAddress, port, colleague);
    }

    public NioClientThread(InetAddress hostAddress, int port) throws IOException {
        super(hostAddress, port);
    }

    @Override
    public void run() {

    }
}
