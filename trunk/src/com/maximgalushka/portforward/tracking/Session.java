package com.maximgalushka.portforward.tracking;

import com.maximgalushka.portforward.config.PortForwardRecord;

import java.io.Serializable;

/**
 *
 * @author mgalushka
 */
public class Session implements Serializable{

    private static final long serialVersionUID = 123456L;

    private String clientAddress;
    private PortForwardRecord record;

    public Session(PortForwardRecord record, String clientAddress) {
        this.record = record;
        this.clientAddress = clientAddress;
    }

    public Session() {
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public PortForwardRecord getRecord() {
        return record;
    }

    public void setRecord(PortForwardRecord record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return String.format("Session = [%s]\n", record.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Session other = (Session) obj;
        if (this.record != other.record && (this.record == null || !this.record.equals(other.record))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.record != null ? this.record.hashCode() : 0);
        return hash;
    }

    

}
