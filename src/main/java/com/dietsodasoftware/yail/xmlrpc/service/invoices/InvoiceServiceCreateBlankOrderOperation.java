package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.LinkedList;
import java.util.List;

@InfusionsoftRpc(service = "InvoiceService", method = "createBlankOrder")
public class InvoiceServiceCreateBlankOrderOperation extends InfusionsoftXmlRpcServiceOperation<Integer> {

    private final CreateBlankOrderArguments arguments;

    public InvoiceServiceCreateBlankOrderOperation(CreateBlankOrderArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> args = new LinkedList<Object>();
        return ListFactory.quickUnmodifiableLinkedList(
                arguments.invoiceId,
                arguments.title,
                arguments.orderDate,
                arguments.leadAffiliateId,
                arguments.saleAffiliateId
        );
    }


    @Override
    public Integer parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse != null){
            return (Integer)rawResponse;
        } else {
            return null;
        }
    }
}
