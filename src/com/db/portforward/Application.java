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

    public void start() throws ApplicationException{

        try {
            global = new GlobalProperties(PathUtils.getConfigurationFile(SERVER_PROPERTIES));
            
            Collection<PortForwardRecord> records = PortForwardConfiguration.getConfigurationManager().getConfiguration();

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
            System.exit(1);
        } 

        ManagementServer ms = new ManagementServer();
        ms.initManagement();
    }

    public static GlobalProperties getGlobalProperties() {
        return global;
    }
}
