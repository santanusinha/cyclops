package com.cyclops.common;

import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 16/09/13
 * Time: 3:19 AM
 */
public class ClusterListener implements MembershipListener {
    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        System.out.println("ADDED MEMBER: " + membershipEvent.getMember().getInetSocketAddress());
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        System.out.println("REMOVED MEMBER: " + membershipEvent.getMember().getInetSocketAddress());
    }

}
