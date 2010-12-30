package com.db.portforward.mgmt.client;

import com.db.portforward.mgmt.*;
import com.db.portforward.mgmt.gui.*;
import com.db.portforward.utils.ThreadUtils;
import com.db.portforward.utils.PathUtils;
import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.config.ConfigurationException;

import java.io.IOException;
import java.io.File;
import java.util.concurrent.*;
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

    public static void main(String[] args) throws IOException {
        ManagementClient client = null;
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
                    createAndShowGUI(model, sessionBean);
                }
            });

            ClientSessionMonitoringThread th = new ClientSessionMonitoringThread(model, sessionBean);
            Future f = threadUtils.scheduleAtFixedRate(th, 1, TimeUnit.SECONDS);

            Thread.sleep(100000);
            f.cancel(true);            
            
        } catch (Exception e) {
            log.error(e);
        } finally{
            if(client != null){
                client.close();
            }
        }
    }

     private static void createAndShowGUI(AbstractTableModel model, SessionManagerMBean bean) {
        //Create and set up the window.
        JFrame frame = new JFrame("Remote monitoring");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
