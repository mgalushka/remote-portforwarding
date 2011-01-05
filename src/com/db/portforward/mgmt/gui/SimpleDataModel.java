/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.gui;

import com.db.portforward.tracking.Session;
import com.db.portforward.tracking.SimpleStandardMBean;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class SimpleDataModel extends AbstractTableModel{

    private static final long serialVersionUID = -6731665899501568990L;

    private static Log log = LogFactory.getLog(SimpleDataModel.class);

    private SimpleStandardMBean mbean;
    private List<Session> cachedSessionsList = new ArrayList<Session>();
    
    public SimpleDataModel(SimpleStandardMBean bean){
        this.mbean = bean;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return cachedSessionsList.size();
    }

    public Object getValueAt(int row, int col) {
        Session s = cachedSessionsList.get(row);
        switch(col){
            case 0: return Integer.toString(row+1);
            case 1: return s.getRecord().getSourcePort();
            case 2: return s.getRecord().getTargetUrl();
            default: return "";
        }
    }

    @Override
    public void fireTableDataChanged() {
        cachedSessionsList = (List<Session>) mbean.getActiveSessions();
        super.fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }



}
