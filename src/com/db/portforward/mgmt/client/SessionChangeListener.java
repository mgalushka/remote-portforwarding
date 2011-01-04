package com.db.portforward.mgmt.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.NotificationListener;
import javax.management.Notification;

/**
 * @author Maxim Galushka
 * @since 04.01.2011
 */
public class SessionChangeListener implements NotificationListener {

    private static Log log = LogFactory.getLog(SessionChangeListener.class);
    
    public void handleNotification(Notification notification, Object handback) {
        log.debug(String.format("Notification: %s\n", notification.getMessage()));
//        log.debug(String.format("Sessions details = %s", mbean.getActiveSessions()));
    }
}
