package com.maximgalushka.portforward.utils;

import com.maximgalushka.portforward.config.ConfigurationException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author mgalushka
 */
public class PathUtils {

    public static String getCurrentJarFilePath() throws URISyntaxException{
        return PathUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
    }

    @Deprecated
    public static File getFile(String path) throws MalformedURLException{
        URL url = new URL("file://" + path);
        File resultFile = null;
        try {
            resultFile = new File(url.toURI());
        } catch(URISyntaxException e) {
            resultFile = new File(url.getPath());
        }
        return resultFile;
    }

    public static File getConfigurationFile(String name) throws ConfigurationException{
        try {
            String currentJarPath = new File(PathUtils.getCurrentJarFilePath()).getParent();
            return new File(
                    currentJarPath + File.separator + name
              );
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

}
