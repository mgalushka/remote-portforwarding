package com.db.portforward;

import com.db.portforward.config.*;
import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.utils.*;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.logging.*;
import org.enterprisepower.net.portforward.Listener;

/**
 *
 * @author mgalushka
 */
public class Application {

    private static final String SERVER_PROPERTIES = "server.properties";

    private static GlobalProperties global;

    private static Log log = LogFactory.getLog(Application.class);
    private static final ThreadUtils threadUtils = ThreadUtils.getInstance();

    private ConfigurationManager<PortForwardRecord> configurationManager;

    public Application(ConfigurationManager<PortForwardRecord> configurationManager) {
        this.configurationManager = configurationManager;
    }

    /**
     * Runs the server application. Inits application from server config.
     * @throws ApplicationException if configuration is not found???
     */
    public void start() throws ApplicationException{

        try {
            global = new GlobalProperties(PathUtils.getConfigurationFile(SERVER_PROPERTIES));
            
            Collection<PortForwardRecord> records = getConfigurationManager().getConfiguration();
            if(records == null){
                throw new ApplicationException("Configuration manager returned null configuration");
            }

            for (PortForwardRecord record : records) {
                try {
                    Listener listener = new Listener(record);
                    threadUtils.scheduleThread(listener);
                } catch (IOException iOException) {
                    log.error(iOException);
                }
            }
        }
        catch (ConfigurationException configurationException) {
            log.error(configurationException);
            throw new ApplicationException(configurationException);
        } 

        ManagementServer ms = new ManagementServer();
        ms.initManagement();
    }

    private ConfigurationManager<PortForwardRecord> getConfigurationManager()
            throws ApplicationException {
        
        if(configurationManager == null){
            throw new ApplicationException("Configuration manager is not set up");
        }
        return this.configurationManager;
    }

    /**
     * Should be set up before any of {@link #start()} method call
     * @param configurationManager current configuration manager object
     */
    public void setConfigurationManager(ConfigurationManager<PortForwardRecord> configurationManager) {
        this.configurationManager = configurationManager;
    }

    public static GlobalProperties getGlobalProperties() {
        return global;
    }
}
