package com.db.portforward.tracking;

import com.db.portforward.ManagementServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mgalushka
 */
public class SessionManager
        implements Manager<Session> {    

    private static final Manager instance = new SessionManager();
    private volatile List<Session> sessions;

    public static synchronized Manager getInstance(){
        return instance;
    }

    private SessionManager() {
        sessions = new ArrayList<Session>();
    }

    public synchronized int getSessionsCount() {
        return sessions.size();
    }

    public synchronized Collection<Session> getActiveSessions() {
        return sessions;
    }

    public synchronized void addSession(Session session) {
        sessions.add(session);
        ManagementServer.refreshSessions();
    }

    public synchronized boolean dropSession(Session session) {
        boolean result = sessions.remove(session);  
        ManagementServer.refreshSessions();
        return result;
    }

}