package com.cyclops.streaming;

import com.cyclops.common.RandomIndexSelector;
import com.hazelcast.core.HazelcastInstance;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 6:34 PM
 */
public class AtmosphereUtil {
    private static RandomIndexSelector<HazelcastInstance> hazelcastSelector;
    private static ExecutorService executorService;

    public static void init(ArrayList<HazelcastInstance> hazelcastInstances, ExecutorService executor) {
        hazelcastSelector = new RandomIndexSelector<HazelcastInstance>(hazelcastInstances);
        executorService = executor;
    }

    public static RandomIndexSelector<HazelcastInstance> getHazelcastSelector() {
        return hazelcastSelector;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
