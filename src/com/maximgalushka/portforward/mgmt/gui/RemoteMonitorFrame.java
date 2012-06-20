package com.maximgalushka.portforward.mgmt.gui;

import com.maximgalushka.portforward.ApplicationException;
import com.maximgalushka.portforward.mgmt.client.ManagementClient;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.management.*;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Main window of monitoring GUI application
 *
 * @author mgalushka
 */
public class RemoteMonitorFrame extends JFrame{

    private static Log log = LogFactory.getLog(RemoteMonitorFrame.class);

    public RemoteMonitorFrame(final AbstractTableModel sessionsModel,
                              final AbstractTableModel connectionsModel,
                              final ManagementClient client) throws HeadlessException {
        
        super("Remote monitoring");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    if(client != null){
                        log.debug("Close client");
                        client.close();
                    }
                } catch (ApplicationException e1) {
                    log.error(e1);
                }
                finally{
                    System.exit(0);
                }
            }
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane contentTabbedPane = new JTabbedPane();

        SessionsTableView sessionsMonitoringTab = new SessionsTableView(sessionsModel);
        contentTabbedPane.addTab("Sessions", sessionsMonitoringTab);

        try {
            ManagementView managementView = new ManagementView(this, connectionsModel,
                                                                client.getConnectionMgmtBean(connectionsModel));

            contentTabbedPane.addTab("Management", managementView);
        } catch (Exception e) {
            log.error(String.format("Cannot create Connection MGMT bean: %s", e.getCause()));
        }

        contentTabbedPane.setOpaque(true);
        this.setContentPane(contentTabbedPane);
    }

}
