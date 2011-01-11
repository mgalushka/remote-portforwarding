package com.db.portforward.mgmt;

import com.db.portforward.config.ConfigurationException;
import com.db.portforward.config.PortForwardRecord;

import javax.management.NotificationEmitter;

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
}
