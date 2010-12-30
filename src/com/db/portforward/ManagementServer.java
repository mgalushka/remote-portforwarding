/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward;

import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.mgmt.SessionManagerMBean;
import com.db.portforward.mgmt.SessionManagerMBeanImpl;
import com.db.portforward.tracking.SessionManager;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.StandardMBean;
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

    public void initManagement(){
        try {
            // Instantiate the MBean server
            //
            log.debug("Create the MBean server");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();

            // Create a JMXMP connector server
            //
            log.debug("Create a JMXMP connector server");
            JMXServiceURL url = new JMXServiceURL("jmxmp", null, GlobalProperties.RMI_PORT);
            JMXConnectorServer cs =
                JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

            // Start the JMXMP connector server
            //
            log.debug("Start the JMXMP connector server");
            cs.start();
            log.debug("JMXMP connector server successfully started");
            log.debug("Waiting for incoming connections...");


            log.debug("Creating SessionManagerMBean instance");
            SessionManagerMBean impl = new SessionManagerMBeanImpl(SessionManager.getInstance());
            log.debug("Creating StandardMBean instance");
            StandardMBean mbean = new StandardMBean(impl, SessionManagerMBean.class);

            log.debug("Creating ObjectName");
            ObjectName mbeanName = new ObjectName("MBeans:type=SessionManagerMBeanImpl");
            //mbs.createMBean("test.jmx.mgmt.SessionManagerMBeanImpl", mbeanName, null, null);
            log.debug("Register MBean");
            mbs.registerMBean(mbean, mbeanName);

            log.debug("Session MBean registered");

        } catch (Throwable e) {
            log.debug("Exception occured!!!");
            log.error(e);
            e.printStackTrace();
        }
    }

}
