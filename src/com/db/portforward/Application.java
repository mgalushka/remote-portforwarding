package com.db.portforward;

import com.db.portforward.config.*;
import com.db.portforward.config.global.GlobalProperties;
import com.db.portforward.utils.ThreadUtils;
import com.db.portforward.utils.PathUtils;

import java.io.IOException;
import java.io.File;
import java.util.Collection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

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
            this.global = new GlobalProperties(getServerConfigurationFile());
            
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

        //threadUtils.scheduleAtFixedRate(new SessionsLogger(), 10, TimeUnit.SECONDS);
        ManagementServer ms = new ManagementServer();
        ms.initManagement();
    }

    public static GlobalProperties getGlobalProperties() {
        return global;
    }

    private static File getServerConfigurationFile() throws ConfigurationException{
        try {
            return PathUtils.getFile(
                    PathUtils.getCurrentJarFilePath() + File.separator + SERVER_PROPERTIES
              );
        } catch (Exception e) {
            log.error(e);
            throw  new ConfigurationException(e);
        }
    }
}
