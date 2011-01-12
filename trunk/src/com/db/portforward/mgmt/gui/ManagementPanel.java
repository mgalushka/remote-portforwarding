package com.db.portforward.mgmt.gui;

import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.logging.*;


/**
 *
 * @author mgalushka
 */
public class ManagementPanel extends JPanel{

    private static Log log = LogFactory.getLog(ManagementPanel.class);

    public ManagementPanel(final JFrame frame) {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JButton b = new JButton("Add");
        b.setActionCommand("Add");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Add")){
                    log.debug("Add configuration button");
                    AddConnectionDialog dialog = new AddConnectionDialog(frame);
                    dialog.pack();
                    dialog.setVisible(true);
                }
            }
        });
        add(b);
    }

}
