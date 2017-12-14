package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * https://developer.infusionsoft.com/docs/read/Invoice_Service#addOrderItem
 */
@InfusionsoftRpc(service = "InvoiceService", method = "addOrderItem")
public class InvoiceServiceAddOrderItemOperation extends InfusionsoftXmlRpcServiceOperation<Boolean> {

    private final AddOrderItemArguments arguments;

    public InvoiceServiceAddOrderItemOperation(AddOrderItemArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        return ListFactory.quickUnmodifiableLinkedList(
                arguments.invoiceId,
                arguments.productId,
                arguments.type.getOrderItemTypeDbId(),
                arguments.price,
                arguments.quantity,
                arguments.title,
                arguments.description
        );
    }


    @Override
    public Boolean parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse != null){
            return (Boolean)rawResponse;
        } else {
            return null;
        }
    }
}
