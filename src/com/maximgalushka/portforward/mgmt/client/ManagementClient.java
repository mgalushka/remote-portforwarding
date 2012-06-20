package com.maximgalushka.portforward.mgmt.client;

import com.maximgalushka.portforward.ApplicationException;
import com.maximgalushka.portforward.config.global.GlobalProperties;
import static com.maximgalushka.portforward.config.global.GlobalConstants.*;
import static com.maximgalushka.portforward.config.global.GlobalConstants.Client.*;

import com.maximgalushka.portforward.mgmt.ConnectionMgmtMBean;
import com.maximgalushka.portforward.mgmt.SessionMgmtMBean;
import com.maximgalushka.portforward.utils.MgmtObjectsFactory;
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
    private SessionChangeListener listener;
    
    void initManagementClient() throws IOException, MalformedObjectNameException, InstanceNotFoundException {
        log.debug("Create a JMXMP connector client and " +
                           "connect it to the JMXMP connector server");

        JMXServiceURL url = new JMXServiceURL(PROTOCOL,
                                    properties.getStringProperty(JMXMP_HOST),
                                    properties.getIntProperty(JMXMP_PORT));

        jmxc = JMXConnectorFactory.connect(url, null);

        log.debug("Get an MBeanServerConnection");
        mbsc = jmxc.getMBeanServerConnection();
    }

    public SessionMgmtMBean getSessionMgmtBean(AbstractTableModel model)
            throws MalformedObjectNameException, ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException, InstanceNotFoundException {

        log.debug("Get SessionMgmt MBean from server...");
        ObjectName sessionMBeanName = MgmtObjectsFactory.getSessionObjectName();
        SessionMgmtMBean proxy =
                MBeanServerInvocationHandler.newProxyInstance(
                        mbsc,
                        sessionMBeanName,
                        SessionMgmtMBean.class,
                        false);

        log.debug("Add remote notification listener");
        listener = new SessionChangeListener(model);
        this.mbsc.addNotificationListener(sessionMBeanName, listener, null, null);

        return proxy;
    }

    public ConnectionMgmtMBean getConnectionMgmtBean(/* TODO: add GUI model */)
            throws MalformedObjectNameException, ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException, InstanceNotFoundException {

        log.debug("Get ConnectionMgmt MBean from server...");
        ObjectName connectionMBeanName = MgmtObjectsFactory.getConnectionObjectName();
        ConnectionMgmtMBean proxy =
                MBeanServerInvocationHandler.newProxyInstance(
                        mbsc,
                        connectionMBeanName,
                        ConnectionMgmtMBean.class,
                        false);

        log.debug("Add remote notification listener");
        this.mbsc.addNotificationListener(connectionMBeanName, listener, null, null);

        return proxy;
    }

    public void close() throws ApplicationException{
        try {
            log.debug("Remove all remote notification listeners");
            mbsc.removeNotificationListener(MgmtObjectsFactory.getSessionObjectName(), listener);
        } catch (Exception ex){
            log.error(ex);
            throw new ApplicationException(ex);
        }

        try {
            log.debug("Close the connection to the server");
            jmxc.close();
        } catch (Exception ex) {
            log.error(ex);
            throw new ApplicationException(ex);
        }
    }


}
