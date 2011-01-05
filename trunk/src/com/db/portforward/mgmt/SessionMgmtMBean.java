package com.db.portforward.mgmt;

import javax.management.NotificationEmitter;
import java.util.Collection;

/**
 * @author Maxim Galushka
 * @since 28.12.2010
 */
public interface SessionMgmtMBean extends NotificationEmitter {

    int getCount();

    Collection getSessions();

    /**
     * Sends notification that sessions list has been changed
     */
    void refresh();
}
