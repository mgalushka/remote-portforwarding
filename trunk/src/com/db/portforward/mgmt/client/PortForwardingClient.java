package com.db.portforward.mgmt.client;

import com.db.portforward.config.global.GlobalProperties;
import static com.db.portforward.config.global.GlobalConstants.Client.*;
import com.db.portforward.mgmt.*;
import com.db.portforward.mgmt.gui.*;
import com.db.portforward.utils.ThreadUtils;
import com.db.portforward.utils.PathUtils;
import com.db.portforward.config.ConfigurationException;

import java.io.IOException;
import java.io.File;
import java.util.concurrent.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

            global = new GlobalProperties(getClientConfigurationFile());

            client = new ManagementClient();
            client.initManagementClient();

            final SessionManagerMBean sessionBean = client.getSessionManagementBean();
            final AbstractTableModel model = new SimpleDataModel(sessionBean);

            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(model);
                }
            });

            ClientSessionMonitoringThread clientSessionMonitoringThread = new ClientSessionMonitoringThread(model, sessionBean);
            refreshScheduler = threadUtils.scheduleAtFixedRate(clientSessionMonitoringThread,
                    global.getIntProperty(REFRESH_FREEQUENCY), TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error(e);
        }
    }

    private static void createAndShowGUI(AbstractTableModel model) {
        //Create and set up the window.
        final JFrame frame = new JFrame("Remote monitoring");

        frame.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}

            public void windowClosing(WindowEvent e) {}

            public void windowClosed(WindowEvent e) {
                log.debug("Cancelling scheduler");
                refreshScheduler.cancel(true);
                try {
                    log.debug("Close client");
                    client.close();
                    System.exit(0);
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
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

    private static File getClientConfigurationFile() throws ConfigurationException {
        try {
            return PathUtils.getFile(
                    PathUtils.getCurrentJarFilePath() + File.separator + CLIENT_PROPERTIES
            );
        } catch (Exception e) {
            log.error(e);
            throw  new ConfigurationException(e);
        }
    }


}
