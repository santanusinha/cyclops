package com.cyclops.resources;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.monitor.LocalTopicStats;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 15/09/13
 * Time: 2:49 AM
 */
@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
public class TopicInfoResource {

    @GET
    @Path("/topics")
    public Response getAllTopics() {
        Set<String> topics = new HashSet<String>();
        for(HazelcastInstance instance: Hazelcast.getAllHazelcastInstances()) {
            for(DistributedObject distObject : instance.getDistributedObjects()) {
                if(distObject.getServiceName().endsWith("topicService")) {
                    topics.add(distObject.getName());
                }
            }
        }
        return Response.ok().entity(topics).build();
    }

    @GET
    @Path("/topics/{topic}")
    public Response getTopicDetail(@PathParam("topic") final String topic) {
        Set<String> topics = new HashSet<String>();
        Set<HazelcastInstance> instances = Hazelcast.getAllHazelcastInstances();
        for(HazelcastInstance instance: instances) {
            for(DistributedObject distObject : instance.getDistributedObjects()) {
                if(distObject.getServiceName().endsWith("topicService")) {
                    topics.add(distObject.getName());
                }
            }
        }
        HashMap<String,LocalTopicStats> stats = new HashMap<String, LocalTopicStats>();
        if(topics.contains(topic)) {
            for(HazelcastInstance instance: instances) {
                LocalTopicStats topicStats = instance.getTopic(topic).getLocalTopicStats();
                stats.put(instance.getName(), topicStats);
            }
        }
        return Response.ok().entity(stats).build();
    }

}
