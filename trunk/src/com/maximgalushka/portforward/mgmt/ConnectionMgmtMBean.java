package com.maximgalushka.portforward.mgmt;

import com.maximgalushka.portforward.config.ConfigurationException;
import com.maximgalushka.portforward.config.PortForwardRecord;

import javax.management.NotificationEmitter;
import java.util.Collection;
import java.util.List;

/**
 * @author Maxim Galushka
 * @since 11.01.2011
 */
public interface ConnectionMgmtMBean extends NotificationEmitter {

    /**
     * @param connection connection details
     * @return true if connection was created successfully
     * @throws ConfigurationException if configuraion is wrong or connection cannot be created
     */
    boolean createConnection(PortForwardRecord connection)
            throws ConfigurationException;

    /**
     * @param connection connection details to drop
     */
    void dropConnection(PortForwardRecord connection);

    /**
     * @return list of all connections currently configured
     */
    List<PortForwardRecord> listConnections();
}
