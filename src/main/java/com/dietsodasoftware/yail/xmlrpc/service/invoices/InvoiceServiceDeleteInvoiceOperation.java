package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:59 AM
 */
public class InvoiceServiceDeleteInvoiceOperation extends SimpleRpcServiceOperation<Boolean> {
    private static final String RPC_NAME = "InvoiceService.deleteInvoice";

    public InvoiceServiceDeleteInvoiceOperation(Integer invoiceId){
        super(RPC_NAME, invoiceId);
    }
}
