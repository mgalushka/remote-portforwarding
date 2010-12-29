/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.gui;

import com.db.portforward.mgmt.SessionManagerMBean;
import com.db.portforward.tracking.Session;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mgalushka
 */
public class SimpleDataModel extends AbstractTableModel{

    private SessionManagerMBean mbean;

    public SimpleDataModel(SessionManagerMBean bean){
        this.mbean = bean;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return mbean.getCount();
    }

    public Object getValueAt(int row, int col) {
        List list = (List) mbean.getSessions();
        Session s = (Session) list.get(row);
        switch(col){
            case 0: return Integer.toString(row+1);
            case 1: return s.getRecord().getSourcePort();
            case 2: return s.getRecord().getTargetUrl();
            default: return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }



}
