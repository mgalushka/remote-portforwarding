/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.mgmt.client;

import com.db.portforward.tracking.SimpleStandardMBean;

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
    private SimpleStandardMBean mbean;
    
    public ClientSessionMonitoringThread(AbstractTableModel model, SimpleStandardMBean bean){
        this.mbean = bean;
        this.model = model;
    }

    public void run() {
        log.debug("Sessions count = " + mbean.getSessionsCount());
        log.debug(String.format("Sessions details = %s", mbean.getActiveSessions()));
        model.fireTableDataChanged();
    }

}
