package com.cyclops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 12:55 PM
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class.getSimpleName());
    public static void main(String args[]) {
        try {
            final String hostName = InetAddress.getLocalHost().getCanonicalHostName();
            CyclopsService cyclops = new CyclopsService(hostName);
            cyclops.run(args);
        } catch (UnknownHostException e) {
            logger.error("Could not find local hostname.. Are you serious?!! ", e);
        } catch (Exception e) {
            logger.error("Unhandled error: ", e);
        }
    }
}
