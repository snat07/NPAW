package com.npaw.plugin_clusters;

import com.npaw.plugin_clusters.data.DataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PluginClustersApplication {

    public static void main(String[] args) {
        DataManager.getInstance();
        SpringApplication.run(PluginClustersApplication.class, args);

    }



}
