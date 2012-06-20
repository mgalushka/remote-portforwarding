package com.maximgalushka.portforward.tracking;

import java.util.Collection;

/**
 *
 * @author mgalushka
 */
public interface Manager<S> {

    /**
     * @return number of currently running sessions
     */
    int getSessionsCount();

    /**
     * @return list of currently running sessions
     */
    Collection<S> getActiveSessions();

    /**
     * Creates new portforwarding session
     * @param session session to create
     */
    void createSession(S session);

    /**
     * Drops existing running session
     * @param session session to drop
     * @return true if session was successfully dropped
     */
    boolean dropSession(S session);

}
