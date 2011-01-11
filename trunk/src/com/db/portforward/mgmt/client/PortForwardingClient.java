package com.db.portforward.mgmt.client;

import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.mgmt.gui.*;
import com.db.portforward.mgmt.SessionMgmtMBean;
import com.db.portforward.utils.*;

import java.io.IOException;
import java.awt.event.*;
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
    private static ManagementClient client;

    public static void main(String[] args) throws IOException {

        try {

            global = new GlobalProperties(PathUtils.getConfigurationFile(CLIENT_PROPERTIES));

            client = new ManagementClient();
            client.initManagementClient();

            final SimpleDataModel model = new SimpleDataModel();
            final SessionMgmtMBean sessionMgmtBean = client.getSessionMgmtBean(model);
            model.setMbean(sessionMgmtBean);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(model);
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            log.error("Client error ocurred: ", e);
        }
    }

    private static void createAndShowGUI(AbstractTableModel model) {
        //Create and set up the window.
        final JFrame frame = new JFrame("Remote monitoring");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    log.debug("Close client");
                    client.close();
                } catch (IOException e1) {
                    log.error(e1);
                }
                finally{
                    System.exit(0);
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //JOptionPane pane = new JOption Pane(); 

        //Create and set up the content pane.
        SimpleTableView newContentPane = new SimpleTableView(model);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static GlobalProperties getGlobalProperties() {
        return global;
    }

}
