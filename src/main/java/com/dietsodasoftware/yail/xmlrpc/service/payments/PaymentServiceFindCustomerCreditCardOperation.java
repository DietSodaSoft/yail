package com.dietsodasoftware.yail.xmlrpc.service.payments;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 11:09 AM
 */
@InfusionsoftRpc(service = "InvoiceService", method = "locateExistingCard")
public class PaymentServiceFindCustomerCreditCardOperation extends SimpleRpcServiceOperation<Integer> {

    public PaymentServiceFindCustomerCreditCardOperation(Integer contactId, String last4){
        super(contactId, last4);
    }

    // I want to hide IS's implementation detail that 0 means not found.  Null means not found.
    @Override
    public Integer parseResult(Object rawResult) throws InfusionsoftAuthorizationFailureException, InfusionsoftResponseParsingException {
        final Integer result = super.parseResult(rawResult);
        if(result == null || result == 0){
            return null;
        }
        return result;
    }
}
