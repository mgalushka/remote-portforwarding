/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.gui;

import com.db.portforward.tracking.Session;
import com.db.portforward.tracking.Manager;
import com.db.portforward.mgmt.SessionMgmtMBean;

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

    private static final long serialVersionUID = 1425493434978344286L;

    private static Log log = LogFactory.getLog(SimpleDataModel.class);

    private SessionMgmtMBean mbean;
    private List<Session> cachedSessionsList = new ArrayList<Session>();


    public SimpleDataModel() {
    }

//    public SimpleDataModel(SessionMgmtMBean bean){
//        this.mbean = bean;
//    }

    public void setMbean(SessionMgmtMBean mbean) {
        this.mbean = mbean;
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
        cachedSessionsList = (List<Session>) mbean.getSessions();
        super.fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }



}
