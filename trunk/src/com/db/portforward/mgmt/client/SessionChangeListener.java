package com.db.portforward.mgmt.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.NotificationListener;
import javax.management.Notification;
import javax.swing.table.AbstractTableModel;

/**
 * @author Maxim Galushka
 * @since 04.01.2011
 */
public class SessionChangeListener implements NotificationListener {

    private static Log log = LogFactory.getLog(SessionChangeListener.class);

    private AbstractTableModel model;

    public SessionChangeListener(AbstractTableModel model){
        this.model = model;
    }

    public void handleNotification(Notification notification, Object handback) {
        log.debug(String.format("Notification: %s\n", notification.getMessage()));
        this.model.fireTableDataChanged();
    }
}
