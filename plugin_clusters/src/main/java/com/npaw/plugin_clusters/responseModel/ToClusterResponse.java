package com.npaw.plugin_clusters.responseModel;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.UUID;

@JacksonXmlRootElement(localName="q")
public class ToClusterResponse {

    @JacksonXmlProperty(localName= "h")
    private String host;
    @JacksonXmlProperty(localName= "pt")
    private float pingTime;
    @JacksonXmlProperty(localName= "c")
    private UUID code;

    public ToClusterResponse(String host, float pingTime) {
        this.host = host;
        this.pingTime = pingTime;
        this.code = UUID.randomUUID();
    }

    public String getHost() {
        return host;
    }

    public float getPingTime() {
        return pingTime;
    }

    public UUID getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "<Host: " + host + " > <PingTime: " + pingTime + " >";
    }
}
