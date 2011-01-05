/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward;

import com.db.portforward.tracking.SessionManager;
import com.db.portforward.tracking.SimpleStandardMBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
@Deprecated
public class SessionsLogger implements Runnable{

    private static Log log = LogFactory.getLog(SessionsLogger.class);
    private static final SimpleStandardMBean sessionManager = SessionManager.getInstance();
    
    public void run() {
        log.debug(String.format("Current sessions count: %d", sessionManager.getSessionsCount()));
    }

}
