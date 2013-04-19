package com.dietsodasoftware.yail.xmlrpc.service.subscriptions;

import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * The ISAPI defines this on InvoiceService.  What?
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:34 AM
 */
public class SubscriptionServiceDeleteSubscriptionOperation extends SimpleRpcServiceOperation<Boolean> {
    private static final String RPC_NAME = "InvoiceService.deleteSubscription";

    public SubscriptionServiceDeleteSubscriptionOperation(Integer subscriptionId) {
        super(RPC_NAME, subscriptionId);
    }

}
