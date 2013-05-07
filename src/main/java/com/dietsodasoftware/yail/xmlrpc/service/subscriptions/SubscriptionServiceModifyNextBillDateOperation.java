package com.dietsodasoftware.yail.xmlrpc.service.subscriptions;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.InfusionsoftDateTimeService;
import org.joda.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 2:13 PM
 */
@InfusionsoftRpc(service = "InvoiceService", method = "updateJobRecurringNextBillDate")
public class SubscriptionServiceModifyNextBillDateOperation extends SimpleRpcServiceOperation<Boolean> {

    public SubscriptionServiceModifyNextBillDateOperation(InfusionsoftDateTimeService dateService,
                                                          Integer subscriptionOrderId,
                                                          LocalDate nextBillingDate) {
        super(subscriptionOrderId, dateBindingValue(dateService, nextBillingDate.toDateTimeAtStartOfDay()));
    }
}
