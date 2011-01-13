package com.db.portforward.mgmt.client;

import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.mgmt.gui.*;
import com.db.portforward.mgmt.SessionMgmtMBean;
import com.db.portforward.utils.*;

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

            final SessionsTableModel model = new SessionsTableModel();
            final SessionMgmtMBean sessionMgmtBean = client.getSessionMgmtBean(model);
            model.setMbean(sessionMgmtBean);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(model, client);
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Client error ocurred: ", e);
        }
    }

    private static void createAndShowGUI(AbstractTableModel model, final ManagementClient client) {
        final RemoteMonitorFrame frame = new RemoteMonitorFrame(model, client);

        //Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static GlobalProperties getGlobalProperties() {
        return global;
    }

}
