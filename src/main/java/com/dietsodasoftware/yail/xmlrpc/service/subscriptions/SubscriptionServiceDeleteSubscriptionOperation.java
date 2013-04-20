package com.dietsodasoftware.yail.xmlrpc.service.subscriptions;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * The ISAPI defines this on InvoiceService.  What?
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:34 AM
 */
@InfusionsoftRpc(service = "InvoiceService", method = "deleteSubscription")
public class SubscriptionServiceDeleteSubscriptionOperation extends SimpleRpcServiceOperation<Boolean> {

    public SubscriptionServiceDeleteSubscriptionOperation(Integer subscriptionId) {
        super(subscriptionId);
    }

}
