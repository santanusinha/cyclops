package com.cyclops.config;

import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 2:11 PM
 */
public class CyclopsConfiguration extends Configuration {
    @Valid
    @NotNull
    private ClusterConfig cluster = new ClusterConfig();

    public CyclopsConfiguration() {
        super();
        //this.cluster = new ClusterConfig();
    }

    public ClusterConfig getCluster() {
        return cluster;
    }
}
