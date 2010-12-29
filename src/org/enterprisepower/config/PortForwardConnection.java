package org.enterprisepower.config;

/**
 *
 * @author mgalushka
 */
@Deprecated
public class PortForwardConnection {

    private static final long serialVersionUID = 1234567L;

    private String sourcePort;
    private String targetUrl;

    public PortForwardConnection() {
    }

    public PortForwardConnection(String sourcePort, String targetUrl) {
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
   

}
