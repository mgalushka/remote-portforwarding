package com.db.portforward.mgmt.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author mgalushka
 */
public class SessionsTableView extends JPanel {

    private static final String [] HEADERS =
            new String[] {"#", "Client IP", "Source Port", "Target URL"};

    public SessionsTableView(TableModel model){
        super(new GridLayout(1,0));

        TableColumnModel columnModel = new DefaultTableColumnModel();

        int headerId = 0;
        for(String header : HEADERS){
            TableColumn column = new TableColumn(headerId++);
            column.setHeaderValue(header);
            columnModel.addColumn(column);
        }
        columnModel.getColumn(0).setMaxWidth(25);
        columnModel.getColumn(1).setMaxWidth(70);
        columnModel.getColumn(2).setMaxWidth(70);

        final JTable table = new JTable(model, columnModel);

        table.setPreferredScrollableViewportSize(new Dimension(500, 500));

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);

        

    }

}
