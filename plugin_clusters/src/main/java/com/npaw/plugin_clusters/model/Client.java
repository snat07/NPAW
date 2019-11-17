package com.npaw.plugin_clusters.model;

import java.util.List;

public class Client {

    private String accountCode;
    private List<Plugin> plugins;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }
}
