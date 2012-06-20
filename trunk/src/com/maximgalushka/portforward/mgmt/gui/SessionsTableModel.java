package com.maximgalushka.portforward.mgmt.gui;

import com.maximgalushka.portforward.tracking.Session;
import com.maximgalushka.portforward.mgmt.SessionMgmtMBean;

import java.util.List;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class SessionsTableModel extends AbstractTableModel{

    private static final long serialVersionUID = 1425493434978344286L;

    private static Log log = LogFactory.getLog(SessionsTableModel.class);

    private SessionMgmtMBean mbean;
    private List<Session> cachedSessionsList = new ArrayList<Session>();


    public SessionsTableModel() {
    }

    public void setMbean(SessionMgmtMBean mbean) {
        this.mbean = mbean;
    }

    public int getColumnCount() {
        return 4;
    }

    public int getRowCount() {
        return cachedSessionsList.size();
    }

    public Object getValueAt(int row, int col) {
        Session s = cachedSessionsList.get(row);
        switch(col){
            case 0: return Integer.toString(row+1);
            case 1: return s.getClientAddress();            
            case 2: return s.getRecord().getSourcePort();
            case 3: return s.getRecord().getTargetUrl();
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
