package org.enterprisepower.net.portforward;

import com.maximgalushka.portforward.Application;
import com.maximgalushka.portforward.ApplicationException;
import com.maximgalushka.portforward.config.PortForwardConfigurationManager;
import com.maximgalushka.portforward.config.ConfigurationException;

/**
 * 
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
