/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.utils;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;


/**
 *
 * @author mgalushka
 */
public class MgmtObjectsFactory {

    public static ObjectName getSessionObjectName() throws MalformedObjectNameException{
        return new ObjectName("MBeans:type=com.db.portforward.mgmt.SessionMgmt");
    }
}
