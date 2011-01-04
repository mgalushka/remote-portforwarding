package com.db.portforward.mgmt.client;

import com.db.portforward.config.global.GlobalProperties;
import static com.db.portforward.config.global.GlobalConstants.Client.*;
import com.db.portforward.mgmt.gui.*;
import com.db.portforward.utils.*;
import com.db.portforward.tracking.ManagerMBean;

import java.io.IOException;
import java.util.concurrent.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.logging.*;

/**
 * @author mgalushka
 */
public class PortForwardingClient {

    private static Log log = LogFactory.getLog(PortForwardingClient.class);
    private static final ThreadUtils threadUtils = ThreadUtils.getInstance();

    private static final String CLIENT_PROPERTIES = "client.properties";
    private static GlobalProperties global;
    private static ManagementClient client;

    private static Future refreshScheduler;

    public static void main(String[] args) throws IOException {

        try {

            global = new GlobalProperties(PathUtils.getConfigurationFile(CLIENT_PROPERTIES));

            client = new ManagementClient();
            client.initManagementClient();

            final ManagerMBean sessionBean = client.getSessionManagementBean();
            final AbstractTableModel model = new SimpleDataModel(sessionBean);

            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(model);
                }
            });

            // TODO: migrate to pub/sub interaction model to reduce traffic???
            ClientSessionMonitoringThread clientSessionMonitoringThread = new ClientSessionMonitoringThread(model, sessionBean);
            refreshScheduler = threadUtils.scheduleAtFixedRate(clientSessionMonitoringThread,
                    global.getIntProperty(REFRESH_FREEQUENCY), TimeUnit.SECONDS);

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
                log.debug("Cancelling scheduler");
                refreshScheduler.cancel(true);
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
