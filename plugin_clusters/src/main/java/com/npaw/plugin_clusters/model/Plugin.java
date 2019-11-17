package com.npaw.plugin_clusters.model;

import java.util.List;

public class Plugin {

    private String name;
    private List<Version> versions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }
}
