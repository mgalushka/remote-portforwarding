package com.maximgalushka.portforward.mgmt.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.maximgalushka.portforward.config.PortForwardRecord;

/**
 * @author Maxim Galushka
 * @since 11.01.2011
 */
public class AddConnectionDialog extends JDialog implements ActionListener{

    private static Log log = LogFactory.getLog(AddConnectionDialog.class);

    private JTextField portTextField;
    private JTextField urlTextField;

    private PortForwardRecord record;

    public AddConnectionDialog(Frame owner) {
        super(owner, "Add Connection", true);

        setResizable(false);

        //Handle window closing correctly.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets( 2, 2, 2, 2 );
        c.gridx = 0; // Column 0
        c.gridy = 0; // Row 0
        c.gridwidth = 2;
        contentPane.add(new JLabel("Port"), c);

        c.gridx = 2; // Column 2
        c.gridwidth = 3;
        portTextField = new JTextField(10);
        contentPane.add(portTextField, c);

        c.gridx = 0; // Column 0
        c.gridy = 1; // Row 1
        c.gridwidth = 2;
        contentPane.add(new JLabel("URL"), c);

        c.gridx = 2; // Column 2
        c.gridwidth = 3;
        urlTextField = new JTextField(10);
        contentPane.add(urlTextField, c);


        c.gridx = 1; // Column 1
        c.gridy = 2; // Row 2
        c.gridwidth = 2;
        JButton add = new JButton("Add");
        add.setActionCommand("Add");
        add.addActionListener(this);
        contentPane.add(add, c);


        c.gridx = 3; // Column 1
        c.gridwidth = 2;
        JButton cancel = new JButton("Cancel");
        cancel.setActionCommand("Close");
        cancel.addActionListener(this);
        contentPane.add(cancel, c);
        
        setContentPane(contentPane);

    }

    public PortForwardRecord getRecord() {
        return record;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Close")){
            dispose();
        }
        if(e.getActionCommand().equals("Add")){
            log.debug("Add action called");
            dispose();
        }
    }
}
