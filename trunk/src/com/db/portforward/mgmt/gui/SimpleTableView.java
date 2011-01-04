package com.db.portforward.mgmt.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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

        JTableHeader header = new JTableHeader();
        TableColumnModel columnModel = new DefaultTableColumnModel();
        columnModel.addColumn(new TableColumn(0));
        columnModel.addColumn(new TableColumn(1));
        columnModel.addColumn(new TableColumn(2));
        columnModel.getColumn(0).setHeaderValue("#");
        columnModel.getColumn(0).setMaxWidth(25);
        columnModel.getColumn(1).setHeaderValue("Source Port");
        columnModel.getColumn(1).setMaxWidth(70);
        columnModel.getColumn(2).setHeaderValue("Target URL");

        final JTable table = new JTable(model, columnModel);

//        table.setTableHeader(header);
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        //table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);

    }



}
