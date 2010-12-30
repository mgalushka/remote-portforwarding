/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.client;

import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.mgmt.SessionManagerMBean;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.enterprisepower.threads.ThreadUtils;

/**
 *
 * @author mgalushka
 */
public class ManagementClient {

    private static Log log = LogFactory.getLog(PortForwardingClient.class);
    private static final ThreadUtils threadUtils = ThreadUtils.getInstance();

    private JMXConnector jmxc;
    private MBeanServerConnection mbsc;
    
    void initManagementClient() throws MalformedURLException, IOException{
        // Create a JMXMP connector client and
        // connect it to the JMXMP connector server
        log.debug("Create a JMXMP connector client and " +
                           "connect it to the JMXMP connector server");

        String server = "goldtpus28.ru.db.com";
        //String server = null;
        JMXServiceURL url = new JMXServiceURL("jmxmp", server, GlobalProperties.RMI_PORT);
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
