/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.tracking;

import java.util.Collection;

/**
 *
 * @author mgalushka
 */
public interface Manager <S>{

    int getSessionsCount();

    Collection<S> getActiveSessions();

    void addSession(S session);

    boolean dropSession(S session);

}
