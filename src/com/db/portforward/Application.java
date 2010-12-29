package com.db.portforward;

import com.db.portforward.config.*;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.logging.*;
import org.enterprisepower.net.portforward.Listener;
import org.enterprisepower.threads.ThreadUtils;

/**
 *
 * @author mgalushka
 */
public class Application {

    private static Log log = LogFactory.getLog(Application.class);
    private static final ThreadUtils threadUtils = ThreadUtils.getInstance();

    public void start(){

        try {
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

}
