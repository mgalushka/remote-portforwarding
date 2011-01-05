package com.db.portforward.tracking;

import javax.management.NotificationBroadcasterSupport;
import javax.management.Notification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 *
 * @author mgalushka
 */
public class SessionManager
        extends NotificationBroadcasterSupport
        implements SimpleStandardMBean<Session>{

    public static final String SESSION_CHANGE = "session.change";

    private static final SimpleStandardMBean instance = new SessionManager();
    private volatile List<Session> sessions;

    public static synchronized  SimpleStandardMBean getInstance(){
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
        Notification addSessionNotification =
            new Notification(SESSION_CHANGE, this, 0, "New Session was added");

        sessions.add(session);
        sendNotification(addSessionNotification);
    }

    public synchronized boolean dropSession(Session session) {
        Notification removeSessionNotification =
            new Notification(SESSION_CHANGE, this, 0, "Session was dropped");

        boolean result = sessions.remove(session);
        sendNotification(removeSessionNotification);
        return result;
    }

}
