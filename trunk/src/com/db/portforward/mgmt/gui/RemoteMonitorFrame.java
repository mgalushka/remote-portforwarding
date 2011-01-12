/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.gui;

import com.db.portforward.ApplicationException;
import com.db.portforward.mgmt.client.ManagementClient;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author mgalushka
 */
public class RemoteMonitorFrame extends JFrame{

    private static Log log = LogFactory.getLog(RemoteMonitorFrame.class);

    public RemoteMonitorFrame(final AbstractTableModel model,
                              final ManagementClient client) throws HeadlessException {
        
        super("Remote monitoring");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    log.debug("Close client");
                    client.close();
                } catch (ApplicationException e1) {
                    log.error(e1);
                }
                finally{
                    System.exit(0);
                }
            }
        });

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        SimpleTableView newContentPane = new SimpleTableView(model);
        newContentPane.setOpaque(true); 
        this.setContentPane(newContentPane);
    }

}
