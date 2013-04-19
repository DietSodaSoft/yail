package com.dietsodasoftware.yail.xmlrpc.service.payments;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 11:09 AM
 */
public class PaymentServiceFindCustomerCreditCardOperation extends SimpleRpcServiceOperation<Integer> {
    private static final String RPC_NAME = "InvoiceService.locateExistingCard";

    public PaymentServiceFindCustomerCreditCardOperation(Integer contactId, String last4){
        super(RPC_NAME, contactId, last4);
    }

    @Override
    public Integer parseResult(Object rawResult) throws InfusionsoftAuthorizationFailureException, InfusionsoftResponseParsingException {
        final Integer result = super.parseResult(rawResult);
        if(result == null || result == 0){
            return null;
        }
        return result;
    }
}
