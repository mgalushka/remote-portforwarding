package com.db.portforward.mgmt;

import com.db.portforward.tracking.SimpleStandardMBean;

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

    private SimpleStandardMBean manager;

    public SessionManagerMBeanImpl(SimpleStandardMBean manager) {
        this.manager = manager;
    }

    public int getCount() {
        return manager.getSessionsCount();
    }

    public Collection getSessions() {
        return manager.getActiveSessions();
    }
}
