package com.dietsodasoftware.yail.xmlrpc.service.subscriptions;

import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.InfusionsoftDateTimeService;
import org.joda.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 2:13 PM
 */
public class SubscriptionServiceModifyNextBillDateOperation extends SimpleRpcServiceOperation<Boolean> {
    private static final String RPC_NAME = "InvoiceService.updateJobRecurringNextBillDate";

    public SubscriptionServiceModifyNextBillDateOperation(String rpcName,
                                                          InfusionsoftDateTimeService dateService,
                                                          Integer subscriptionOrderId,
                                                          LocalDate nextBillingDate) {
        super(rpcName, subscriptionOrderId, dateBindingValue(dateService, nextBillingDate.toDateTimeAtStartOfDay()));
    }
}
