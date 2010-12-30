package com.db.portforward.mgmt.client;

import com.db.portforward.config.global.GlobalProperties;
import static com.db.portforward.config.global.GlobalConstants.*;
import static com.db.portforward.config.global.GlobalConstants.Client.*;
import com.db.portforward.mgmt.SessionManagerMBean;
import java.io.IOException;
import javax.management.*;
import javax.management.remote.*;
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
    
    void initManagementClient() throws IOException{
        // Create a JMXMP connector client and
        // connect it to the JMXMP connector server
        log.debug("Create a JMXMP connector client and " +
                           "connect it to the JMXMP connector server");

        String server = "goldtpus28.ru.db.com";
        //String server = null;
        JMXServiceURL url = new JMXServiceURL(PROTOCOL,
                                    properties.getStringProperty(JMXMP_HOST),
                                    properties.getIntProperty(JMXMP_PORT));

        this.jmxc = JMXConnectorFactory.connect(url, null);

        // Get an MBeanServerConnection
        log.debug("Get an MBeanServerConnection");
        this.mbsc = jmxc.getMBeanServerConnection();

        // Get domains from MBeanServer
        log.debug("Domains:");
        String domains[] = mbsc.getDomains();
        for (int i = 0; i < domains.length; i++) {
            log.debug("\tDomain[" + i + "] = " + domains[i]);
        }
    }

    public SessionManagerMBean getSessionManagementBean() throws MalformedObjectNameException, ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException{

        // Create SimpleStandard MBean
        ObjectName mbeanName = new ObjectName("MBeans:type=SessionManagerMBeanImpl");
        log.debug("Create SimpleStandard MBean...");

        // Another way of interacting with a given MBean is through a
        // dedicated proxy instead of going directly through the MBean
        // server connection
        SessionManagerMBean proxy = (SessionManagerMBean)
                MBeanServerInvocationHandler.newProxyInstance(
                                         mbsc,
                                         mbeanName,
                                         SessionManagerMBean.class,
                                         false);

        return proxy;
    }

    public void close() throws IOException{
        // Close MBeanServer connection
        log.debug("Close the connection to the server");
        this.jmxc.close();
        log.debug("Bye! Bye!");
    }



}
