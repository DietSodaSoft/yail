package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:38 AM
 */
public class InvoiceServiceCalculateInvoiceBalanceOperation extends SimpleRpcServiceOperation<Double> {
    private final static String RPC_NAME = "InvoiceService.calculateAmountOwed";

    public InvoiceServiceCalculateInvoiceBalanceOperation(Integer invoiceId) {
        super(RPC_NAME, invoiceId);
    }

}
