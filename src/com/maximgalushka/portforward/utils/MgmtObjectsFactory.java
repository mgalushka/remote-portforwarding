/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maximgalushka.portforward.utils;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;


/**
 *
 * @author mgalushka
 */
public class MgmtObjectsFactory {

    public static ObjectName getSessionObjectName() throws MalformedObjectNameException{
        return new ObjectName("MBeans:type=com.maximgalushka.portforward.mgmt.SessionMgmt");
    }

    public static ObjectName getConnectionObjectName() throws MalformedObjectNameException{
        return new ObjectName("MBeans:type=com.maximgalushka.portforward.mgmt.ConnectionMgmt");
    }
}
