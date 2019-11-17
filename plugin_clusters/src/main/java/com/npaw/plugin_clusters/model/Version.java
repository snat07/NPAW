package com.npaw.plugin_clusters.model;

import java.util.List;

public class Version {

    private String number;
    private int pingTime;
    private List<Cluster> clusters;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPingTime() {
        return pingTime;
    }

    public void setPingTime(int pingTime) {
        this.pingTime = pingTime;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }
}
