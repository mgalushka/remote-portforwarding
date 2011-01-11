package com.db.portforward.mgmt.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Maxim Galushka
 * @since 11.01.2011
 */
public class AddConnectionDialog extends JDialog {

    private Container cp;

    public AddConnectionDialog(Frame owner) {
        super(owner, "Add Connection", true);

        cp = getContentPane();

        cp.setLayout(new FlowLayout(FlowLayout.CENTER));

        cp.add(new JButton("Add"));
    }
}
