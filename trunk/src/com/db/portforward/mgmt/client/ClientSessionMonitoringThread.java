/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.client;

import com.db.portforward.tracking.Manager;
import com.db.portforward.mgmt.SessionMgmtMBean;

import javax.swing.table.AbstractTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class ClientSessionMonitoringThread implements Runnable{

    private static Log log = LogFactory.getLog(ClientSessionMonitoringThread.class);

    private AbstractTableModel model;
    private SessionMgmtMBean mbean;
    
    public ClientSessionMonitoringThread(AbstractTableModel model, SessionMgmtMBean bean){
        this.mbean = bean;
        this.model = model;
    }

    public void run() {
        log.debug("Sessions count = " + mbean.getCount());
        log.debug(String.format("Sessions details = %s", mbean.getSessions()));
        model.fireTableDataChanged();
    }

}
