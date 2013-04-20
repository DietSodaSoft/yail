package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:59 AM
 */
@InfusionsoftRpc(service = "InvoiceService", method = "deleteInvoice")
public class InvoiceServiceDeleteInvoiceOperation extends SimpleRpcServiceOperation<Boolean> {

    public InvoiceServiceDeleteInvoiceOperation(Integer invoiceId){
        super(invoiceId);
    }
}
