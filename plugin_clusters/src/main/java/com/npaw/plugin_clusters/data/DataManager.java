package com.npaw.plugin_clusters.data;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npaw.plugin_clusters.exception.NotFoundException;
import com.npaw.plugin_clusters.model.Client;
import com.npaw.plugin_clusters.model.Cluster;
import com.npaw.plugin_clusters.model.Plugin;
import com.npaw.plugin_clusters.model.Version;
import com.npaw.plugin_clusters.responseModel.ToClusterResponse;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataManager {
    private static final String DATA_URL = "pluginClusters.json";
    private static DataManager instance = new DataManager();
    private List<Client> clients;
    private Date loadedFileTimeStamp;

    private DataManager() {
        loadData();
    }

    public static DataManager getInstance() {
        return instance;
    }

    private void loadData() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Client> clientList = mapper.readValue(new File(DATA_URL), new TypeReference<List<Client>>(){});
            clients = clientList;
            loadedFileTimeStamp = new Date();

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadDataIfNeeded() {
        File file = new File(DATA_URL);
        Date lastModified = new Date(file.lastModified());
        if (loadedFileTimeStamp.before(lastModified)) {
            loadData();
        }
    }

    public ToClusterResponse toClusterResponse(String accountCode, String targetDevice, String pluginVersion) throws NotFoundException{

        loadDataIfNeeded();
        Client client = findClientByAccountCode(accountCode);
        if (Objects.isNull(client)) {
            throw new NotFoundException("No client with this account code");
        }
        else {
            Plugin plugin = findPluginByName(client, targetDevice);
            if (Objects.isNull(plugin)) {
                throw new NotFoundException("No plugin for this client with this target device");
            }
            else {
                Version version = findVersionByNumber(plugin, pluginVersion);
                if (Objects.isNull(version)) {
                    throw new NotFoundException("No version for this plugin with this number");
                }
                else {
                    String host = loadBalanceHost(version.getClusters());
                    if (Objects.isNull(host)) {
                        throw new NotFoundException("Error definition in the hosts. Reach system administrator");
                    }
                    else {
                        return new ToClusterResponse(host, version.getPingTime());
                    }
                }
            }
        }
    }

    public Client findClientByAccountCode(String accuntCode) {
        return clients.stream().filter(client -> accuntCode.equals(client.getAccountCode())).findFirst().orElse(null);
    }

    public Plugin findPluginByName(Client client, String name) {
        return client.getPlugins().stream().filter(plugin -> name.equals(plugin.getName())).findFirst().orElse(null);
    }

    public Version findVersionByNumber(Plugin plugin, String pluginVersion) {
        return plugin.getVersions().stream().filter(version -> pluginVersion.equals(version.getNumber())).findFirst().orElse(null);
    }

    public List<Cluster> validateClusters(List<Cluster> clusters) {
        return clusters.stream().filter(cluster -> cluster.getWeight() > 0 && cluster.getWeight() <= 1).collect(Collectors.toList());
    }

    private String loadBalanceHost(List<Cluster> clusters) {
        clusters = validateClusters(clusters);
        if (clusters.size() > 0) {
            Cluster nextCluster = clusters.stream().max(Comparator.comparing(cluster -> cluster.getWeightAccumulated())).get();
            nextCluster.setWeightAccumulated(nextCluster.getWeightAccumulated() - 1);
            clusters.forEach(cluster -> cluster.setWeightAccumulated(cluster.getWeightAccumulated() + cluster.getWeight()));
            return nextCluster.getHost();
        }
        return null;
    }

}
