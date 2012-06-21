package com.maximgalushka.nioserver;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class ServerDataEvent {

    protected DatagramChannel server;
    protected ByteBuffer socket;
    protected SocketAddress data;

    public ServerDataEvent(NioServer server, SocketChannel socket, byte[] dataCopy) {
    }
}
