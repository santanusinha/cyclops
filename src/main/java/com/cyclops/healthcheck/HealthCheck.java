package com.cyclops.healthcheck;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 15/09/13
 * Time: 3:04 AM
 */
public class HealthCheck extends com.yammer.metrics.core.HealthCheck {
    public HealthCheck() {
        super("cyclops");
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
