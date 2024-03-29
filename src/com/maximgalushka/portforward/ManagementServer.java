package com.maximgalushka.portforward;

import com.maximgalushka.portforward.config.global.GlobalProperties;
import com.maximgalushka.portforward.mgmt.SessionMgmt;
import com.maximgalushka.portforward.mgmt.ConnectionMgmt;
import com.maximgalushka.portforward.mgmt.ConnectionMgmtMBean;
import static com.maximgalushka.portforward.config.global.GlobalConstants.*;
import com.maximgalushka.portforward.utils.MgmtObjectsFactory;

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

            mbs.createMBean(ConnectionMgmt.class.getName(),
                            MgmtObjectsFactory.getConnectionObjectName(), null, null);
            log.debug("Connection MBean registered");           


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

    public ConnectionMgmtMBean getConnectionsMgmtBean() throws ApplicationException{
        try {
            ConnectionMgmtMBean proxy =
                MBeanServerInvocationHandler.newProxyInstance(
                        mbsc,
                        MgmtObjectsFactory.getConnectionObjectName(),
                        ConnectionMgmtMBean.class,
                        false);

            return proxy;
        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException("Cannot create proxy object", e);
        }
    }

    /**
     * Implement correct resource management and closing
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mbs.unregisterMBean(MgmtObjectsFactory.getSessionObjectName());
        cs.stop();
    }



}
