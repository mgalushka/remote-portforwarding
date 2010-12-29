/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.config;

import com.db.portforward.utils.PathUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
public class PortForwardConfiguration implements ConfigurationManager<PortForwardRecord>{

    private static Log log = LogFactory.getLog(PortForwardConfiguration.class);

    private static PortForwardConfiguration instance;

    private static final String CONFIGURATION_FILE = "portforward.cfg";
    private static final String COMMENT_SYMBOL = "#";

    private List<PortForwardRecord> config = new ArrayList<PortForwardRecord>();
    private File storage;
    private volatile boolean saved = true;

    public static synchronized ConfigurationManager getConfigurationManager() 
            throws ConfigurationException{
        
        if(instance == null){
            instance = new PortForwardConfiguration();
        }
        return instance;
    }

    private PortForwardConfiguration() throws ConfigurationException{
        try {
            load();
        } catch (Exception ex) {
            log.error("Config error: ", ex);
            throw new ConfigurationException(ex);
        }
    }

    public void add(PortForwardRecord configuration) {
        config.add(configuration);
        saved = false;
    }

    public synchronized boolean remove(PortForwardRecord configuration) {
        boolean result = config.remove(configuration);
        if(result) {
            saved = false;
        }
        return result;
    }

    public Collection<PortForwardRecord> getConfiguration() {
        return config;
    }

    protected synchronized void persist() throws IOException {
        if(saved) return;
        else{
            PrintWriter pw = new PrintWriter(this.storage);
            for(PortForwardRecord record : config){
                pw.printf("%s:%s\n", record.getSourcePort(), record.getTargetUrl());
            }
            pw.close();
            saved = true;
        }
    }

    protected synchronized void load() throws MalformedURLException,
                                 URISyntaxException,
                                 FileNotFoundException,
                                 IOException {

        String path = PathUtils.getCurrentJarFilePath();
        path = path.substring(0, path.lastIndexOf('/')+1);

        this.storage = PathUtils.getFile(path + File.separator + CONFIGURATION_FILE);

        log.debug(String.format("Path: %s", storage.getPath()));

        if(!this.storage.exists()){
            this.storage.createNewFile();
            return;
        }

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
