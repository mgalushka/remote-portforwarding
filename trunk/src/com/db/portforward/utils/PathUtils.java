package com.db.portforward.utils;

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

}
