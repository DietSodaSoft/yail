package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:27 AM
 */
public class InvoiceServiceCreateSubscriptionInvoiceOperation extends SimpleRpcServiceOperation<Integer> {
    private static final String RPC_NAME = "InvoiceService.createInvoiceForRecurring";

    public InvoiceServiceCreateSubscriptionInvoiceOperation(Integer subscriptionOrderId) {
        super(RPC_NAME, subscriptionOrderId);
    }

}
