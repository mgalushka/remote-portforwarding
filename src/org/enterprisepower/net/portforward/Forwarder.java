package org.enterprisepower.net.portforward;

import com.db.portforward.Application;
import com.db.portforward.ApplicationException;
import com.db.portforward.config.PortForwardConfigurationManager;
import com.db.portforward.config.ConfigurationException;

/**
 * 
 * @author Kenneth Xu
 * 
 */
public class Forwarder {

    /**
     * @param args empty for now
     * @throws ApplicationException if error occures during application start
     * @throws ConfigurationException if configuration is not found
     */
    public static void main(String[] args) throws ApplicationException, ConfigurationException {
        new Application(PortForwardConfigurationManager.getConfigurationManager()).start();
        
    }
}
