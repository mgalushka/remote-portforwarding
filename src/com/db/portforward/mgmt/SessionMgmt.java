package com.db.portforward.mgmt;

import com.db.portforward.tracking.Manager;
import com.db.portforward.tracking.SessionManager;
import com.db.portforward.ManagementServer;

import java.util.Collection;
import javax.management.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JMX Management bean implementation to monitor sessions on server
 *
 * @author Maxim Galushka
 * @since 28.12.2010
 */
public class SessionMgmt
            extends NotificationBroadcasterSupport
            implements SessionMgmtMBean {

    private static Log log = LogFactory.getLog(SessionMgmt.class);

    public static final String SESSION_CHANGE = "session.change";
    private Manager manager = SessionManager.getInstance();

//    public static SessionMgmtMBean getServerMgmtInstance()
//            throws MalformedObjectNameException, InstanceNotFoundException {
//
//        ObjectName mbeanName = new ObjectName("MBeans:type=com.db.portforward.mgmt.SessionMgmt");
//        log.debug("Get SessionMgmt MBean from server...");
//
////        return (SessionMgmt) MBeanServerInvocationHandler.newProxyInstance(
////                        ManagementServer.getServer(),
////                        mbeanName,
////                        SessionMgmtMBean.class,
////                        false);
//
//        SessionMgmtMBean proxy =
//                MBeanServerInvocationHandler.newProxyInstance(
//                        ManagementServer.getServer(),
//                        mbeanName,
//                        SessionMgmtMBean.class,
//                        false);
//
//        return proxy;//ManagementServer.getServer().getObjectInstance(mbeanName).
//    }

    public SessionMgmt() {
    }

    public int getCount() {
        return manager.getSessionsCount();
    }

    public Collection getSessions() {
        return manager.getActiveSessions();
    }

    public void refresh() {
        Notification addSessionNotification =
                new Notification(SESSION_CHANGE, this, 0, "New Session was added");
        sendNotification(addSessionNotification);
    }
}
