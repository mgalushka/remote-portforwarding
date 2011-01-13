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
        contentPane.add( new JLabel( "Port" ), c );

        c.gridx = 2; // Column 2
        c.gridwidth = 3;
        contentPane.add( new JTextField(10), c );

        c.gridx = 0; // Column 0
        c.gridy = 1; // Row 1
        c.gridwidth = 2;
        contentPane.add( new JLabel( "URL" ), c );

        c.gridx = 2; // Column 2
        c.gridwidth = 3;
        contentPane.add( new JTextField(10), c );


        c.gridx = 1; // Column 1
        c.gridy = 2; // Row 2
        c.gridwidth = 2;
        contentPane.add( new Button("Add"), c );


        c.gridx = 3; // Column 1
        c.gridwidth = 2;
        contentPane.add( new Button("Cancel"), c );

        
        setContentPane(contentPane);

    }

}
