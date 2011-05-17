/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.config;

/**
 *
 * @author mgalushka
 */
public class ConfigurationException extends RuntimeException{

    private static final long serialVersionUID = 5165608627375637327L;

    ConfigurationException(Exception ex) {
        super("Configuration exception");
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
