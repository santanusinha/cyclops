package com.cyclops.streaming.atmospherehelpers;

import com.cyclops.common.Event;
import com.cyclops.pubsub.TopicListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 4:46 PM
 */
public class WebSocketListener extends WebSocketEventListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketListener.class.getSimpleName());
    private HazelcastInstance hazelcastInstance;
    private ExecutorService executorService;
    private Broadcaster topic;
    private String listenerId;

    public WebSocketListener(HazelcastInstance hazelcastInstance, ExecutorService executorService, Broadcaster topic) {
        this.hazelcastInstance = hazelcastInstance;
        this.executorService = executorService;
        this.topic = topic;
    }

    @Override
    public void onSuspend(AtmosphereResourceEvent event) {
        super.onSuspend(event);
        ITopic<Event> distTopic = hazelcastInstance.getTopic(topic.getID());
        listenerId = distTopic.addMessageListener(new TopicListener(topic, executorService));
        logger.info("Subscribed");
    }

    @Override
    public void onBroadcast(AtmosphereResourceEvent event) {
        super.onBroadcast(event);
        logger.info("BROADCASTING**** " + ((Event)event.getMessage()).getMessage());
    }

    @Override
    public void onDisconnect(WebSocketEvent event) {
        super.onDisconnect(event);
        ITopic<Event> distTopic = hazelcastInstance.getTopic(topic.getID());
        distTopic.removeMessageListener(listenerId);
        logger.info("UnSubscribed");
    }
}
