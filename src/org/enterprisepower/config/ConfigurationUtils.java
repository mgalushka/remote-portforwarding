/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.enterprisepower.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mgalushka
 */
@Deprecated
public class ConfigurationUtils {

    private static Log log = LogFactory.getLog(ConfigurationUtils.class);

    private static final String CONFIGURATION_FILE = "portforward.cfg";
    private static final String COMMENT_SYMBOL = "#";

    public static final List<PortForwardConnection> readProperties() 
            throws FileNotFoundException, IOException, URISyntaxException{
        
        List<PortForwardConnection> result = new ArrayList<PortForwardConnection>();
        //Object o = ConfigurationUtils.class.getResourceAsStream(CONFIGURATION_FILE);

        String path = ConfigurationUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

        URL url = new URL("file://" + path.substring(0, path.lastIndexOf('/')+1) +"/" + CONFIGURATION_FILE);
        File configFile;
        try {
            configFile = new File(url.toURI());
        } catch(URISyntaxException e) {
            configFile = new File(url.getPath());
        }

        BufferedReader br = new BufferedReader(new FileReader(configFile));

        String line = "";
        while((line = br.readLine()) != null){
            log.debug(line);
            if(!line.startsWith(COMMENT_SYMBOL)){
                String sourcePort = line.substring(0, line.indexOf(":"));
                String targetUrl = line.substring(sourcePort.length()+1);
                result.add(new PortForwardConnection(sourcePort, targetUrl));
            }
        }

        return result;
    }

}
