package com.db.portforward.mgmt;

import com.db.portforward.tracking.Manager;
import java.util.Collection;
import javax.management.NotificationBroadcasterSupport;

/**
 * @author Maxim Galushka
 * @since 28.12.2010
 */
public class SessionManagerMBeanImpl
            extends NotificationBroadcasterSupport
            implements SessionManagerMBean {

    private Manager manager;

    public SessionManagerMBeanImpl(Manager manager) {
        this.manager = manager;
    }

    public int getCount() {
        return manager.getSessionsCount();
    }

    public Collection getSessions() {
        return manager.getActiveSessions();
    }
}
