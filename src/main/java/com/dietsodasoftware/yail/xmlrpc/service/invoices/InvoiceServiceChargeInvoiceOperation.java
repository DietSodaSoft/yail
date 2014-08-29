package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * https://developer.infusionsoft.com/docs/read/Invoice_Service#chargeInvoice
 */
@InfusionsoftRpc(service = "InvoiceService", method = "chargeInvoice")
public class InvoiceServiceChargeInvoiceOperation extends InfusionsoftXmlRpcServiceOperation<ChargeInvoiceResult> {

    private final ChargeInvoiceArguments arguments;

    public InvoiceServiceChargeInvoiceOperation(ChargeInvoiceArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> args = new LinkedList<Object>();
        return ListFactory.quickUnmodifiableLinkedList(
                arguments.invoiceId,
                arguments.notes,
                arguments.creditCardId,
                arguments.merchantAccountId,
                arguments.bypassCommissions
        );
    }


    @Override
    public ChargeInvoiceResult parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        final Map<String, String> rawMap = (Map<String, String>) rawResponse;
        return new ChargeInvoiceResult(rawMap);
    }
}
