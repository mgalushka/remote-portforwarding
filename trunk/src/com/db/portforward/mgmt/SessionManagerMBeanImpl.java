package com.db.portforward.mgmt;

import com.db.portforward.tracking.ManagerMBean;

import java.util.Collection;
import javax.management.NotificationBroadcasterSupport;

/**
 * @author Maxim Galushka
 * @since 28.12.2010
 */
@Deprecated
public class SessionManagerMBeanImpl
            extends NotificationBroadcasterSupport
            implements SessionManagerMBean {

    private ManagerMBean manager;

    public SessionManagerMBeanImpl(ManagerMBean manager) {
        this.manager = manager;
    }

    public int getCount() {
        return manager.getSessionsCount();
    }

    public Collection getSessions() {
        return manager.getActiveSessions();
    }
}
