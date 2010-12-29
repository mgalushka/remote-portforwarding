/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward;

import com.db.portforward.tracking.Manager;
import com.db.portforward.tracking.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class SessionsLogger implements Runnable{

    private static Log log = LogFactory.getLog(SessionsLogger.class);
    private static final Manager sessionManager = SessionManager.getInstance();
    
    public void run() {
        log.debug(String.format("Current sessions count: %d", sessionManager.getSessionsCount()));
    }

}
