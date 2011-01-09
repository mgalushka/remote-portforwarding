package com.db.portforward.config;

import com.db.portforward.utils.PathUtils;
import java.io.*;
import java.util.*;
import org.apache.commons.logging.*;

/**
 *
 * @author mgalushka
 */
public class PortForwardConfigurationManager implements ConfigurationManager<PortForwardRecord>{

    private static Log log = LogFactory.getLog(PortForwardConfigurationManager.class);

    private static PortForwardConfigurationManager instance;

    private static final String CONFIGURATION_FILE = "portforward.cfg";
    private static final String COMMENT_SYMBOL = "#";

    private List<PortForwardRecord> config = new ArrayList<PortForwardRecord>();
    private File storage;

    /**
     * Factory method to return cingle instance of configuration manager
     * @return ConfigurationManager instance
     * @throws ConfigurationException if config does not exist
     */
    public static synchronized ConfigurationManager<PortForwardRecord> getConfigurationManager() 
            throws ConfigurationException{
        
        if(instance == null){
            instance = new PortForwardConfigurationManager();
            try {
                instance.load();
            } catch (IOException e) {
                log.error("Exception during loading of Configuration", e);
                throw new ConfigurationException(e);
            }
        }
        return instance;
    }

    private PortForwardConfigurationManager(){
    }

    public void add(PortForwardRecord configuration) {
        config.add(configuration);
    }

    public synchronized boolean remove(PortForwardRecord configuration) {
        return config.remove(configuration);
    }

    public Collection<PortForwardRecord> getConfiguration() {
        return config;
    }

    //TODO: why do we need this?
    protected synchronized void persist() throws IOException {
        PrintWriter pw = new PrintWriter(this.storage);
        for(PortForwardRecord record : config){
            pw.printf("%s:%s\n", record.getSourcePort(), record.getTargetUrl());
        }
        pw.close();
    }

    protected synchronized void load() throws ConfigurationException, IOException {

        // TODO: really???
        this.storage = PathUtils.getConfigurationFile(CONFIGURATION_FILE);
        log.debug(String.format("Path: %s\n", storage.getPath()));

//        if(!this.storage.exists()){
//            this.storage.createNewFile();
//            return;
//        }

        BufferedReader br = new BufferedReader(new FileReader(this.storage));

        String line = "";
        while((line = br.readLine()) != null){
            log.debug(line);
            if(!line.startsWith(COMMENT_SYMBOL)){
                String sourcePort = line.substring(0, line.indexOf(":"));
                String targetUrl = line.substring(sourcePort.length()+1);
                this.config.add(new PortForwardRecord(sourcePort, targetUrl));
            }
        }
    }

}
