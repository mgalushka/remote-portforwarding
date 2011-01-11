/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.enterprisepower.net.portforward;

import com.db.portforward.config.PortForwardRecord;
import com.db.portforward.tracking.Session;
import com.db.portforward.utils.ThreadUtils;
import org.apache.commons.logging.*;
import java.net.*;
import java.io.*;
import org.enterprisepower.net.NetUtils;

/**
 * 
 * @author Kenneth Xu
 * 
 */
public class Listener extends Thread{

    private static Log log = LogFactory.getLog(Listener.class);
    private static final ThreadUtils threadUtils = ThreadUtils.getInstance();

    private ServerSocket serverSocket;
    private InetSocketAddress from, to;
    private Cleaner cleaner = new Cleaner();
    private PortForwardRecord record;

    public PortForwardRecord getRecord() {
        return record;
    }

    public Listener(PortForwardRecord connection) throws IOException {
        this(NetUtils.parseInetSocketAddress(connection.getSourcePort()),
                NetUtils.parseInetSocketAddress(connection.getTargetUrl()));
        record = connection;
    }

    public Listener(InetSocketAddress from, InetSocketAddress to)
            throws IOException {
        this.from = from;
        this.to = to;
        serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(from);

        log.info("Ready to accept client connection from " + from.getPort()
                + " to " + to.getHostName() + ":" + to.getPort());
    }

    public void run() {
        Socket source = null;
        threadUtils.scheduleThread(cleaner);
        while (!this.isInterrupted()) {
            try {
                TargetConnector connector = new TargetConnector(to);
                source = serverSocket.accept();
                if(this.isInterrupted()) return;

                log.trace("accepted client connection");
                Socket target = connector.openSocket();
                Session session = new Session(record);
                new Processor(session, source, target, cleaner).process();
            } catch (IOException e) {
                String msg = "Failed to accept client connection on port "
                        + from.getPort();
                log.error(msg, e);
                try {
                    if (source != null && !source.isClosed()) {
                        source.close();
                    }
                } catch (IOException e1) {
                    log.error("Error during closing server socket attempt ", e);
                    throw new ListenerException(e1);
                }
                throw new ListenerException(e);
            }
        }
    }

    public void close() {
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Listener listener = (Listener) o;

        if (!record.equals(listener.record)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return record.hashCode();
    }
}
