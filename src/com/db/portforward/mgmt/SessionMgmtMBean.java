package com.db.portforward.mgmt;

import javax.management.NotificationEmitter;
import java.util.Collection;

/**
 * @author Maxim Galushka
 * @since 28.12.2010
 */
public interface SessionMgmtMBean extends NotificationEmitter {

    /**
     * @return number of active sessions
     */
    int getCount();

    /**
     * @return the list of active session currently runnitng on server
     */
    Collection getSessions();

    /**
     * Sends notification that sessions list has been changed
     */
    void refresh();
}
