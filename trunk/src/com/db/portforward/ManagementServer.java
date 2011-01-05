package com.db.portforward;

import com.db.portforward.config.global.GlobalProperties;
import static com.db.portforward.config.global.GlobalConstants.*;
import com.db.portforward.tracking.SessionManager;

import javax.management.*;
import javax.management.remote.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 *
 * @author mgalushka
 */
public class ManagementServer {
    
    private static Log log = LogFactory.getLog(ManagementServer.class);

    private static MBeanServer mbs;
    private static MBeanServerConnection mbsc;

    private GlobalProperties properties = Application.getGlobalProperties();

    public void initManagement() throws ApplicationException{
        try {
            // Instantiate the MBean server
            log.debug("Create the MBean server");
            mbs = MBeanServerFactory.createMBeanServer();

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

            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            mbsc = jmxc.getMBeanServerConnection();

//            SessionMgmtMBean impl = new SessionMgmt(SessionMgmt.getInstance());
//            StandardMBean mbean = new StandardMBean(impl, SessionMgmtMBean.class);

//            final String[] types = new String[] {SessionManager.SESSION_CHANGE};
//            final MBeanNotificationInfo info = new MBeanNotificationInfo(
//                                                  types,
//                                                  Notification.class.getName(),
//                                                  "Notification about sessions.");

//            final NotificationEmitter emitter =
//                    new NotificationBroadcasterSupport(info);

//            Manager manager = SessionMgmt.getInstance();
//            StandardEmitterMBean mbean = new StandardEmitterMBean(manager, Manager.class, manager);

//            mbs.registerMBean(manager, mbeanName);
            mbs.createMBean("com.db.portforward.mgmt.SessionMgmt", getSessionMgmtBeanName(), null, null);
           
            log.debug("Session MBean registered");

        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException(e);
        }
    }

    public static void refreshSessions() {
        try {
            mbsc.invoke(getSessionMgmtBeanName(), "refresh", null, null);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private static ObjectName getSessionMgmtBeanName() throws MalformedObjectNameException {
        return new ObjectName("MBeans:type=com.db.portforward.mgmt.SessionMgmt");
    }

}
