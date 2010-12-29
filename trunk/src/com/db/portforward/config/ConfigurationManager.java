/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.config;

import java.util.Collection;

/**
 *
 * @author mgalushka
 */
public interface ConfigurationManager <T>{

    void add(T configuration);

    boolean remove(T configuration);

    Collection<T> getConfiguration();

    //void persist();

    //void load();
}
