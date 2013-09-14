package com.cyclops.pubsub;

import com.cyclops.config.ClusterConfig;
import com.hazelcast.core.HazelcastInstance;
import com.yammer.dropwizard.lifecycle.Managed;

import java.util.ArrayList;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 11:16 PM
 */
public class ManagedHazelcast implements Managed {
    private ArrayList<HazelcastInstance> hazelcastInstances;

    public ManagedHazelcast(final ClusterConfig clusterConfig) {

    }


    @Override
    public void start() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stop() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
