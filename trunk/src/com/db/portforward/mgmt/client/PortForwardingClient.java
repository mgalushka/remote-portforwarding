/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.client;

import com.db.portforward.mgmt.SessionManagerMBean;
import com.db.portforward.mgmt.gui.SimpleDataModel;
import com.db.portforward.mgmt.gui.SimpleTableView;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.enterprisepower.threads.ThreadUtils;

/**
 * @author mgalushka
 */
public class PortForwardingClient {

    private static Log log = LogFactory.getLog(PortForwardingClient.class);
    private static final ThreadUtils threadUtils = ThreadUtils.getInstance();

    public static void main(String[] args) throws IOException {
        ManagementClient client = null;
        try {

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


}
