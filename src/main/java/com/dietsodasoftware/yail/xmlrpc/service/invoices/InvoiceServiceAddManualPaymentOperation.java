package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.LinkedList;
import java.util.List;


/**
 * https://developer.infusionsoft.com/docs/read/Invoice_Service#addManualPayment
 */
@InfusionsoftRpc(service = "InvoiceService", method = "addManualPayment")
public class InvoiceServiceAddManualPaymentOperation extends InfusionsoftXmlRpcServiceOperation<Boolean> {

    private final AddManualPaymentArguments arguments;

    public InvoiceServiceAddManualPaymentOperation(AddManualPaymentArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> args = new LinkedList<Object>();
        return ListFactory.quickUnmodifiableLinkedList(
                arguments.invoiceId,
                arguments.amt,
                arguments.paymentDate,
                arguments.paymentType,
                arguments.paymentDescription,
                arguments.bypassCommissions
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
