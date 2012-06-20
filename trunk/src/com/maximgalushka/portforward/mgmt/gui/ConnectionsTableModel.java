package com.maximgalushka.portforward.mgmt.gui;

import com.maximgalushka.portforward.config.PortForwardRecord;
import com.maximgalushka.portforward.mgmt.ConnectionMgmtMBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 20.06.12
 */
public class ConnectionsTableModel extends AbstractTableModel {

    private static Log log = LogFactory.getLog(SessionsTableModel.class);

    private ConnectionMgmtMBean mbean;

    private List<PortForwardRecord> cachedConnectionsList = new ArrayList<PortForwardRecord>();


    public ConnectionsTableModel() {
    }

    public void setMbean(ConnectionMgmtMBean mbean) {
        this.mbean = mbean;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return cachedConnectionsList.size();
    }

    public Object getValueAt(int row, int col) {
        PortForwardRecord s = cachedConnectionsList.get(row);
        switch(col){
            case 0: return Integer.toString(row + 1);
            case 1: return s.getSourcePort();
            case 2: return s.getTargetUrl();
            default: return "";
        }
    }

    @Override
    public void fireTableDataChanged() {
        cachedConnectionsList = mbean.listConnections();
        super.fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
