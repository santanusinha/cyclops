package com.cyclops.pubsub;

import com.cyclops.common.Event;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import org.atmosphere.cpr.Broadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 4:48 PM
 */
public class TopicListener implements MessageListener<Event> {
    private static final Logger logger = LoggerFactory.getLogger(TopicListener.class.getSimpleName());
    private Broadcaster topicBroadcaster;
    private ExecutorService executorService;

    public TopicListener(Broadcaster topicBroadcaster, ExecutorService executorService) {
        this.topicBroadcaster = topicBroadcaster;
        this.executorService = executorService;
        logger.info("Created listener");
    }

    @Override
    public void onMessage(final Message<Event> message) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Future<Object> finish = topicBroadcaster.broadcast(message.getMessageObject());
                    finish.get();
                    logger.debug("Message sent: " + message.getMessageObject().getMessage());
                } catch (InterruptedException e) {
                    logger.error("Broadcast interrupted: ", e);
                } catch (ExecutionException e) {
                    logger.error("Error during broadcasting: ", e.getCause());
                }
            }
        });
    }
}
