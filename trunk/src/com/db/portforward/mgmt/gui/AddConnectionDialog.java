package com.db.portforward.mgmt.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Maxim Galushka
 * @since 11.01.2011
 */
public class AddConnectionDialog extends JDialog {

    public AddConnectionDialog(Frame owner) {
        super(owner, "Add Connection", true);

        //Handle window closing correctly.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();

        contentPane.add(new JTextField(""));
        contentPane.add(new JTextField(""));

        contentPane.add(new JButton("Add"));
        contentPane.add(new JButton("Cancel"));

        setContentPane(contentPane);
        
    }
}
