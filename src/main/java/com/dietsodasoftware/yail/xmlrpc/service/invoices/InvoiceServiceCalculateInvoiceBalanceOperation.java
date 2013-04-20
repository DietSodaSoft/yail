package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:38 AM
 */
@InfusionsoftRpc(service = "InvoiceService", method = "calculateAmountOwed")
public class InvoiceServiceCalculateInvoiceBalanceOperation extends SimpleRpcServiceOperation<Double> {

    public InvoiceServiceCalculateInvoiceBalanceOperation(Integer invoiceId) {
        super(invoiceId);
    }

}
