package com.db.portforward.mgmt.client;

import com.db.portforward.config.global.GlobalProperties;
import static com.db.portforward.config.global.GlobalConstants.*;
import static com.db.portforward.config.global.GlobalConstants.Client.*;
import com.db.portforward.mgmt.SessionMgmtMBean;
import com.db.portforward.utils.MgmtObjectsFactory;
import java.io.IOException;
import javax.management.*;
import javax.management.remote.*;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.*;

/**
 *
 * @author mgalushka
 */
public class ManagementClient {

    private static Log log = LogFactory.getLog(PortForwardingClient.class);

    private GlobalProperties properties = PortForwardingClient.getGlobalProperties();

    private JMXConnector jmxc;
    private MBeanServerConnection mbsc;
    
    void initManagementClient() throws IOException, MalformedObjectNameException, InstanceNotFoundException {
        log.debug("Create a JMXMP connector client and " +
                           "connect it to the JMXMP connector server");

        JMXServiceURL url = new JMXServiceURL(PROTOCOL,
                                    properties.getStringProperty(JMXMP_HOST),
                                    properties.getIntProperty(JMXMP_PORT));

        this.jmxc = JMXConnectorFactory.connect(url, null);

        log.debug("Get an MBeanServerConnection");
        this.mbsc = jmxc.getMBeanServerConnection();

        log.debug("Domains:");
        String domains[] = mbsc.getDomains();
        for (int i = 0; i < domains.length; i++) {
            log.debug("\tDomain[" + i + "] = " + domains[i]);
        }
    }

    public SessionMgmtMBean getSessionMgmtBean(AbstractTableModel model) throws MalformedObjectNameException, ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException, InstanceNotFoundException {

        log.debug("Get SessionMgmt MBean from server...");
        ObjectName sessionMBeanName = MgmtObjectsFactory.getSessionObjectName();
        SessionMgmtMBean proxy =
                MBeanServerInvocationHandler.newProxyInstance(
                        mbsc,
                        sessionMBeanName,
                        SessionMgmtMBean.class,
                        false);

        log.debug("Add notification listener");
        SessionChangeListener listener = new SessionChangeListener(model);        
        this.mbsc.addNotificationListener(sessionMBeanName, listener, null, null);

        return proxy;
    }

    public void close() throws IOException{
        // Close MBeanServer connection
        log.debug("Close the connection to the server");
        this.jmxc.close();
        log.debug("Bye! Bye!");
    }


}
