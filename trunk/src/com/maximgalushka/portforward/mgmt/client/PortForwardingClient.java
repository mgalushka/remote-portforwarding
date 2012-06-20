package com.maximgalushka.portforward.mgmt.client;

import com.maximgalushka.portforward.config.global.GlobalProperties;
import com.maximgalushka.portforward.mgmt.ConnectionMgmtMBean;
import com.maximgalushka.portforward.mgmt.gui.*;
import com.maximgalushka.portforward.mgmt.SessionMgmtMBean;
import com.maximgalushka.portforward.utils.*;

import java.io.IOException;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.logging.*;

/**
 * @author mgalushka
 */
public class PortForwardingClient {

    private static Log log = LogFactory.getLog(PortForwardingClient.class);

    private static final String CLIENT_PROPERTIES = "client.properties";
    private static GlobalProperties global;

    public static void main(String[] args) throws IOException {

        try {
            global = new GlobalProperties(PathUtils.getConfigurationFile(CLIENT_PROPERTIES));

            final ManagementClient client = new ManagementClient();
            client.initManagementClient();

            final SessionsTableModel sessionsModel = new SessionsTableModel();
            final SessionMgmtMBean sessionMgmtBean = client.getSessionMgmtBean(sessionsModel);
            sessionsModel.setMbean(sessionMgmtBean);

            final ConnectionsTableModel connectionsModel = new ConnectionsTableModel();
            final ConnectionMgmtMBean connectionMgmtBean = client.getConnectionMgmtBean(connectionsModel);
            connectionsModel.setMbean(connectionMgmtBean);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(sessionsModel, connectionsModel, client);
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Client error ocurred: ", e);
        }
    }

    private static void createAndShowGUI(AbstractTableModel sessionsModel,
                                         AbstractTableModel connectionsModel,
                                         final ManagementClient client) {

        final RemoteMonitorFrame frame = new RemoteMonitorFrame(sessionsModel,
                                                                connectionsModel,
                                                                client);

        //Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static GlobalProperties getGlobalProperties() {
        return global;
    }

}
