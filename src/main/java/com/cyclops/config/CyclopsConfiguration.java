package com.cyclops.config;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private ClusterConfig cluster;

    public ClusterConfig getCluster() {
        return cluster;
    }
}
