/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.gui;

import com.db.portforward.mgmt.SessionManagerMBean;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author mgalushka
 */
public class SimpleTableView extends JPanel {

    public SimpleTableView(TableModel model){
        super(new GridLayout(1,0));

        String[] columnNames = {"#",
                                "Source Port",
                                "Target Url",
                                };


        //TableModel dataModel = new SimpleDataModel(mbean);


        final JTable table = new JTable(model);
        table.setTableHeader(new JTableHeader());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        //table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);

    }



}
