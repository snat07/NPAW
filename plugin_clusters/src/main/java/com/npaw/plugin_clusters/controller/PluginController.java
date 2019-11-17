package com.npaw.plugin_clusters.controller;

import com.npaw.plugin_clusters.data.DataManager;
import com.npaw.plugin_clusters.exception.NotFoundException;
import com.npaw.plugin_clusters.responseModel.ToClusterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PluginController {

    @GetMapping(path = "/ToCluster", produces = MediaType.APPLICATION_ATOM_XML_VALUE,
    params ={"accountCode", "targetDevice", "pluginVersion"})
    public ResponseEntity getCluster(
            @RequestParam(value = "accountCode") String accountCode,
            @RequestParam(value = "targetDevice") String targetDevice,
            @RequestParam(value = "pluginVersion") String pluginVersion) throws NotFoundException {
        try {
            System.out.println(message(accountCode, targetDevice, pluginVersion));

            ToClusterResponse q = DataManager.getInstance().toClusterResponse(accountCode, targetDevice, pluginVersion);

            System.out.println(q.toString());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(q);
        }
        catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.toString());
        }

    }

    private String message(String accountCode, String targetDevice, String pluginVersion) {
        return "Request for: <" + accountCode + " " + targetDevice + " " + pluginVersion + ">";
    }


}



