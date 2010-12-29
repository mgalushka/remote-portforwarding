/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.db.portforward.config;

import java.io.Serializable;

/**
 *
 * @author mgalushka
 */
public class PortForwardRecord implements Serializable{

    private static final long serialVersionUID = 123456L;

    private String sourcePort;
    private String targetUrl;

    public PortForwardRecord() {
    }

    public PortForwardRecord(String sourcePort, String targetUrl) {
        this.sourcePort = sourcePort;
        this.targetUrl = targetUrl;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public String toString() {
        return String.format("Connection: port=%s, targetUrl=%s", sourcePort, targetUrl);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PortForwardRecord other = (PortForwardRecord) obj;
        if ((this.sourcePort == null) ? (other.sourcePort != null) : !this.sourcePort.equals(other.sourcePort)) {
            return false;
        }
        if ((this.targetUrl == null) ? (other.targetUrl != null) : !this.targetUrl.equals(other.targetUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.sourcePort != null ? this.sourcePort.hashCode() : 0);
        hash = 89 * hash + (this.targetUrl != null ? this.targetUrl.hashCode() : 0);
        return hash;
    }

}
