package com.db.portforward.tracking;

import java.util.Collection;

/**
 *
 * @author mgalushka
 */
public interface Manager<S> {

    int getSessionsCount();

    Collection<S> getActiveSessions();

    void addSession(S session);

    boolean dropSession(S session);

}
