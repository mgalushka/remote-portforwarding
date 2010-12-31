package com.db.portforward.config.global;

import com.db.portforward.config.ConfigurationException;
import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class GlobalProperties {

    private static Log log = LogFactory.getLog(GlobalProperties.class);

    private Properties props = new Properties();

    public GlobalProperties(File properiesFile) throws ConfigurationException {
        try {
            props.load(new BufferedInputStream(
                    new FileInputStream(properiesFile)
            ));
        } catch (IOException e) {
            log.error(e);
            throw new ConfigurationException(e);
        }
    }

    public Integer getIntProperty(String name){
        if(props.containsKey(name)){
            return Integer.parseInt(props.getProperty(name));
        }
        else return null;
    }

    public String getStringProperty(String name){
        return props.getProperty(name);
    }

}
