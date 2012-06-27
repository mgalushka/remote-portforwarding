package com.maximgalushka.nio.nioserver;

import java.nio.channels.SocketChannel;

public class ServerDataEvent {

    protected NioServer server;
    protected SocketChannel socket;
    protected byte[] data;

    public ServerDataEvent(NioServer server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;

        this.server = server;
        this.socket = socket;
        this.data = dataCopy;

    }
}
