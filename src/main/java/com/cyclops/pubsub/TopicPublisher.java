package com.cyclops.pubsub;

import com.cyclops.common.Event;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 1:37 PM
 */
public class TopicPublisher {
    final HazelcastInstance hazelcast;

    public TopicPublisher(HazelcastInstance hazelcast) {
        this.hazelcast = hazelcast;
    }

    public void publish(final String topic, final Event event) {
        ITopic<Event> distTopic = hazelcast.getTopic(topic);
        distTopic.publish(event);
    }
}
