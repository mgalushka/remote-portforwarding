package com.maximgalushka.portforward.mgmt.gui;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import com.maximgalushka.portforward.mgmt.ConnectionMgmtMBean;
import org.apache.commons.logging.*;


/**
 * Panel represented management screen on GUI
 *
 * @author mgalushka
 */
public class ManagementView extends JPanel{

    private static Log log = LogFactory.getLog(ManagementView.class);

    public ManagementView(final JFrame frame,
                          AbstractTableModel connectionsModel,
                          final ConnectionMgmtMBean connectionMBean) {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // ======== CONNECTIONS VIEW ==========//
        ConnectionsTableView connectionsView = new ConnectionsTableView(connectionsModel);
        add(connectionsView);

        // ======== ADD BUTTON ==========//
        JButton b = new JButton("Add");
        b.setActionCommand("Add");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Add")){
                    log.debug("Add configuration button");
                    AddConnectionDialog dialog = new AddConnectionDialog(frame, connectionMBean);
                    dialog.pack();
                    dialog.setVisible(true);
                }
            }
        });
        add(b);

        connectionsModel.fireTableDataChanged();
    }

}
