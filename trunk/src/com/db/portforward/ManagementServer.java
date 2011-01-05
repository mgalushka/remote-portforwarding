package com.db.portforward;

import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.mgmt.client.SessionChangeListener;
import static com.db.portforward.config.global.GlobalConstants.*;
import com.db.portforward.tracking.SessionManager;
import com.db.portforward.tracking.SimpleStandardMBean;
import javax.management.MBeanNotificationInfo;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;
import javax.management.ObjectName;
import javax.management.StandardEmitterMBean;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class ManagementServer {
    
    private static Log log = LogFactory.getLog(ManagementServer.class);

    private GlobalProperties properties = Application.getGlobalProperties();

    public void initManagement() throws ApplicationException{
        try {
            // Instantiate the MBean server
            log.debug("Create the MBean server");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();

            // Create a JMXMP connector server
            log.debug("Create a JMXMP connector server on localhost");
            JMXServiceURL url = new JMXServiceURL(PROTOCOL, null, 
                                    properties.getIntProperty(JMXMP_PORT));

            JMXConnectorServer cs =
                JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

            // Start the JMXMP connector server
            log.debug("Start the JMXMP connector server");
            cs.start();
            log.debug("JMXMP connector server successfully started\nWaiting for incoming connections...");

//            SessionManagerMBean impl = new SessionManagerMBeanImpl(SessionManager.getInstance());
//            StandardMBean mbean = new StandardMBean(impl, SessionManagerMBean.class);

            final String[] types = new String[] {SessionManager.SESSION_CHANGE};
            final MBeanNotificationInfo info = new MBeanNotificationInfo(
                                                  types,
                                                  Notification.class.getName(),
                                                  "Notification about sessions.");

            final NotificationEmitter emitter =
                    new NotificationBroadcasterSupport(info);

            SimpleStandardMBean manager = SessionManager.getInstance();
            StandardEmitterMBean mbean = new StandardEmitterMBean(manager, SimpleStandardMBean.class, manager);

            ObjectName mbeanName = new ObjectName("MBeans:type=com.db.portforward.tracking.SessionManager");
            mbs.registerMBean(manager, mbeanName);
//            mbs.createMBean("com.db.portforward.tracking.SessionManager", mbeanName);

            log.debug("Session MBean registered");

        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException(e);
        }
    }

}
