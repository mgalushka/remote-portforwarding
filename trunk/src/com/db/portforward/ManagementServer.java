package com.db.portforward;

import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.mgmt.SessionMgmt;
import static com.db.portforward.config.global.GlobalConstants.*;
import com.db.portforward.utils.MgmtObjectsFactory;

import javax.management.*;
import javax.management.remote.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class ManagementServer {
    
    private static Log log = LogFactory.getLog(ManagementServer.class);

    private static MBeanServer mbs;
    private static MBeanServerConnection mbsc;

    private JMXConnectorServer cs;

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

            cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

            // Start the JMXMP connector server
            log.debug("Start the JMXMP connector server");
            cs.start();
            log.debug("JMXMP connector server successfully started\nWaiting for incoming connections...");

            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            mbsc = jmxc.getMBeanServerConnection();

            mbs.createMBean(SessionMgmt.class.getName(),
                            MgmtObjectsFactory.getSessionObjectName(), null, null);
           
            log.debug("Session MBean registered");

        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException(e);
        }
    }

    public static void refreshSessions() {
        try {
            mbsc.invoke(MgmtObjectsFactory.getSessionObjectName(), "refresh", null, null);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Implement correct resource management and closing
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        mbs.unregisterMBean(MgmtObjectsFactory.getSessionObjectName());
        cs.stop();
    }



}
