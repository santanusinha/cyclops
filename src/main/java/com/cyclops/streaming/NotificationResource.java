package com.cyclops.streaming;

import com.cyclops.common.Event;
import com.cyclops.streaming.atmospherehelpers.WebSocketListener;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.SuspendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 4:09 PM
 */
@Path("/{topic}")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource {
    private static final Logger logger = LoggerFactory.getLogger(NotificationResource.class.getSimpleName());

    public NotificationResource() {
        logger.info("HERE");
    }

    @GET
    @Suspend(resumeOnBroadcast = true, contentType = MediaType.APPLICATION_JSON)
    public SuspendResponse<Event> subscribe(@PathParam("topic") Broadcaster topic) {
        //final Broadcaster topic = BroadcasterFactory.getDefault().lookup("/" + topicName, true);
        WebSocketListener listener = AtmosphereUtil.getSocketLister(topic);
        if(null == listener) {
        logger.error("MF is null :(");
        }
        return new SuspendResponse.SuspendResponseBuilder<Event>()
                    .broadcaster(topic)
                    .outputComments(true)
                    .addListener(listener)
                    .build();
    }

}
