package com.cyclops.resources;

import com.cyclops.common.Event;
import com.cyclops.common.RandomIndexSelector;
import com.cyclops.pubsub.TopicPublisher;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * User: Santanu Sinha (santanu.sinha@gmail.com)
 * Date: 14/09/13
 * Time: 12:59 PM
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Path("/publish")
public class EventPublisherResource {
    private RandomIndexSelector<TopicPublisher> publisherSelector;

    public EventPublisherResource(ArrayList<TopicPublisher> publisherList) {
        this.publisherSelector = new RandomIndexSelector<TopicPublisher>(publisherList);
    }

    @POST
    @Path("/{topic}")
    public Response publish(@Context HttpServletRequest request,
                        @PathParam("topic")final String topic, final String message) {
        publisherSelector.get().publish(topic, new Event(request.getRemoteAddr(), message));
        return Response.ok().build();
    }

}
