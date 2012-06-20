package com.maximgalushka.portforward.mgmt;

import com.maximgalushka.portforward.config.PortForwardRecord;
import com.maximgalushka.portforward.config.ConfigurationException;
import com.maximgalushka.portforward.utils.ThreadUtils;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

import org.enterprisepower.net.portforward.Listener;

import java.io.IOException;
import java.util.*;

/**
 * @author Maxim Galushka
 * @since 11.01.2011
 */
public class ConnectionMgmt
        extends NotificationBroadcasterSupport
        implements ConnectionMgmtMBean {

    private Set<Listener> listeners = new HashSet<Listener>();
    private Set<PortForwardRecord> records = new HashSet<PortForwardRecord>();

    private static ThreadUtils threadUtils = ThreadUtils.getInstance();

    private static final String CONNECTION_ADDED = "connection.add";
    private static final String CONNECTION_REMOVED = "connection.remove";

    @Override
    public boolean createConnection(PortForwardRecord connection)
            throws ConfigurationException {
                           
        if(records.contains(connection)){
            throw new ConfigurationException(
                    String.format("Listener with configuration: %s is already running", connection));
        }

        Listener listener;
        try {
            listener = new Listener(connection);
            listeners.add(listener);
            records.add(connection);

            Notification addConnectionNotification =
                    new Notification(CONNECTION_ADDED, this, 0, "New Connection was added");
            sendNotification(addConnectionNotification);

        } catch (IOException e) {
            throw new ConfigurationException("Cannot initiate connection, " +
                    "creating Listener error", e);
        }
        threadUtils.scheduleThread(listener);
        return true;
    }

    @Override
    public void dropConnection(PortForwardRecord connection) {
        Listener listener = getListener(connection);
        listener.interrupt();
        
        listeners.remove(listener);
        records.remove(connection);

        Notification removeConnectionNotification =
                new Notification(CONNECTION_REMOVED, this, 0, "New Connection was dropped");
        sendNotification(removeConnectionNotification);
    }

    @Override
    public List<PortForwardRecord> listConnections() {
        return new ArrayList<PortForwardRecord>(records);
    }

    private Listener getListener(PortForwardRecord record){
        for(Listener l : listeners){
            if(l.getRecord().equals(record)) return l;
        }
        return null;
    }
}
