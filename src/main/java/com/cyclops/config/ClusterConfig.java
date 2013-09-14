package com.cyclops.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 2:12 PM
 */
public class ClusterConfig {
    @JsonProperty
    @Valid
    @NotNull
    @NotEmpty
    private String name;

    @JsonProperty
    @Valid
    @Min(1)
    @Max(16)
    private int numMembersPerNode;


    public ClusterConfig() {
        this.name = "cyclops";
        this.numMembersPerNode = 1;
    }

    public int getNumMembersPerNode() {
        return numMembersPerNode;
    }

    public void setNumMembersPerNode(int numMembersPerNode) {
        this.numMembersPerNode = numMembersPerNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
