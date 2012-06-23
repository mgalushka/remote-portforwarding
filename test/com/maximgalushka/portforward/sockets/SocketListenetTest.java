package com.maximgalushka.portforward.sockets;

import javax.net.ServerSocketFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 21.06.12
 */
public class SocketListenetTest {

    public static void main(String[] args) {

        ServerSocketFactory ssf = ServerSocketFactory.getDefault();
        try {

            ServerSocket serverSocket = ssf.createServerSocket();
            serverSocket.setReuseAddress(true);

            InetSocketAddress from = new InetSocketAddress(7777);
            serverSocket.bind(from);

            Socket socket = serverSocket.accept();

            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());

            int b;
            while ((b = is.read()) != -1){
                System.out.printf("%d\n", (byte) b);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
