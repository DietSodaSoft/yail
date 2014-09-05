package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:27 AM
 */
@InfusionsoftRpc(service = "InvoiceService", method = "createInvoiceForRecurring")
public class InvoiceServiceCreateSubscriptionInvoiceOperation extends SimpleRpcServiceOperation<Integer> {

    public InvoiceServiceCreateSubscriptionInvoiceOperation(Integer subscriptionOrderId) {
        super(subscriptionOrderId);
    }

}
