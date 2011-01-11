package com.db.portforward;

import com.db.portforward.config.*;
import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.utils.*;
import com.db.portforward.mgmt.ConnectionMgmtMBean;

import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.logging.*;

/**
 *
 * @author mgalushka
 */
public class Application {

    private static final String SERVER_PROPERTIES = "server.properties";

    private static GlobalProperties global;

    private static Log log = LogFactory.getLog(Application.class);

    private ConfigurationManager<PortForwardRecord> configurationManager;
    private ScheduledExecutorService executorService;

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

            ManagementServer ms = new ManagementServer();
            ms.initManagement();

            Collection<PortForwardRecord> records = getConfigurationManager().getConfiguration();
            if(records == null){
                throw new ApplicationException("Configuration manager returned null configuration");
            }

            ConnectionMgmtMBean mbean = ms.getConnectionsMgmtBean();
            for (PortForwardRecord record : records) {
                try {
                    mbean.createConnection(record);
                }
                catch(ConfigurationException e){
                    log.error(e);
                }
            }
        }
        catch (ConfigurationException configurationException) {
            log.error(configurationException);
            throw new ApplicationException(configurationException);
        } 

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
    
    public void setThreadExecutorService(ScheduledExecutorService service) {
        this.executorService = service;
    }

    public static GlobalProperties getGlobalProperties() {
        return global;
    }
}
