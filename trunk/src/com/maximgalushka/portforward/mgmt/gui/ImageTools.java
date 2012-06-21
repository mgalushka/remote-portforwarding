package com.maximgalushka.portforward.mgmt.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 21.06.12
 */
public class ImageTools {

    private static Log log = LogFactory.getLog(ImageTools.class);

    /**
     * Obtains the image URL
     */
    public static Image createImage(String path, String description) {
        URL imageURL = ImageTools.class.getResource(path);

        log.debug(String.format("Image URL: %s", imageURL));

        if (imageURL == null) {
            log.error(String.format("Resource not found: %s", path));
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
