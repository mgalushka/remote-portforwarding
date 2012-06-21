package com.maximgalushka.portforward.mgmt.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 21.06.12
 */
public class TraySupport {

    private static Log log = LogFactory.getLog(TraySupport.class);

    public TraySupport() {
        SystemTray tray = SystemTray.getSystemTray();

        try {
            TrayIcon icon = new TrayIcon(
                    ImageTools.createImage("/wall-socket-1.gif", "Remote portforwarding control"));

            tray.add(icon);

            final PopupMenu popup = new PopupMenu();
            // Create a popup menu components
            MenuItem sessionsItem = new MenuItem("Sessions");
            MenuItem connectionsItem = new MenuItem("Connections");
            MenuItem exitItem = new MenuItem("Exit");

            //Add components to popup menu
            popup.add(sessionsItem);
            popup.add(connectionsItem);
            popup.addSeparator();
            popup.add(exitItem);

            icon.setPopupMenu(popup);

        } catch (AWTException e) {
            log.error(String.format("Error: %s", e));
        }
    }
}
