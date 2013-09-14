package com.cyclops.streaming;

import com.cyclops.common.RandomIndexSelector;
import com.cyclops.streaming.atmospherehelpers.WebSocketListener;
import com.hazelcast.core.HazelcastInstance;
import org.atmosphere.cpr.Broadcaster;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 6:34 PM
 */
public class AtmosphereUtil {
    private static RandomIndexSelector<HazelcastInstance> hazelcastSelector;
    private static ExecutorService executorService;
    private static ConcurrentHashMap<Broadcaster, WebSocketListener> listenerSockets;

    public static void init(ArrayList<HazelcastInstance> hazelcastInstances, ExecutorService executor) {
        hazelcastSelector = new RandomIndexSelector<HazelcastInstance>(hazelcastInstances);
        executorService = executor;
        listenerSockets = new ConcurrentHashMap<Broadcaster, WebSocketListener>();
    }

    public static WebSocketListener getSocketLister(final Broadcaster topic) {
        WebSocketListener listener = listenerSockets.get(topic);
        if(null == listener) {
            listener = new WebSocketListener(
                    hazelcastSelector.get(), executorService, topic, listenerSockets);
            listenerSockets.putIfAbsent(topic, listener);
        }
        return listener;
    }
}
