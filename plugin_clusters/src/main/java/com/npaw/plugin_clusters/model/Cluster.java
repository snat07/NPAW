package com.npaw.plugin_clusters.model;

public class Cluster {
    private String host;
    private float weight;
    private float weightAccumulated;

    public String getHost() { return host; }

    public void setHost(String host) { this.host = host; }

    public float getWeight() { return weight; }

    public void setWeight(float weight) {
        this.weight = weight;
        this.weightAccumulated = weight;
    }

    public float getWeightAccumulated() { return weightAccumulated; }

    public void setWeightAccumulated(float weightAccumulated) { this.weightAccumulated = weightAccumulated; }
}
