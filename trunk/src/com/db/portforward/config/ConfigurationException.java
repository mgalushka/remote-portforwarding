/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.config;

/**
 *
 * @author mgalushka
 */
public class ConfigurationException extends Exception{

    ConfigurationException(Exception ex) {
        super("Configuration exception");
    }

}
