package com.db.portforward.mgmt;

import java.util.Collection;

/**
 * @author Maxim Galushka
 * @since 28.12.2010
 */
public interface SessionManagerMBean {

    int getCount();

    Collection getSessions();
}
