package com.db.portforward.mgmt;

import com.db.portforward.config.PortForwardRecord;
import com.db.portforward.config.ConfigurationException;
import com.db.portforward.utils.ThreadUtils;

import javax.management.NotificationBroadcasterSupport;

import org.enterprisepower.net.portforward.Listener;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

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
    }

    private Listener getListener(PortForwardRecord record){
        for(Listener l : listeners){
            if(l.getRecord().equals(record)) return l;
        }
        return null;
    }
}
