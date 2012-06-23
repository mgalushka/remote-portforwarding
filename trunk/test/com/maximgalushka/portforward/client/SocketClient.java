package com.maximgalushka.portforward.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 24.06.12
 */
public class SocketClient {

    public static void main(String[] args) {
        if(args.length == 0) return;

        String portStr = args[0];
        if(portStr == null || "".equals(portStr.trim())) return;

        Integer port = Integer.parseInt(portStr);

        try {
            SocketAddress sa = new InetSocketAddress("localhost", port);
            Socket client = new Socket();

            client.connect(sa, 1000);

            System.out.println("Connected successfully");

            BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());

            long i = 100000000L;
            while (--i > 0){
                int bt = 7;

                bos.write(1);
                if( (bt = bis.read()) == -1) break;
                bos.write(bt);

                bos.flush();

                System.out.println("cycle ends");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
