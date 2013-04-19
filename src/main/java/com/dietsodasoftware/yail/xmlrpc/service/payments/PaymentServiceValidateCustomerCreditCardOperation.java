package com.dietsodasoftware.yail.xmlrpc.service.payments;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

import java.util.Map;

/**
 * The InvoiceService.validateCreditCard API has two flavors.  This validates an existing credit card.  There is another
 * operation to validate an incoming CC.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 11:21 AM
 */
public class PaymentServiceValidateCustomerCreditCardOperation extends SimpleRpcServiceOperation<CreditCardValidationResults>{
    private final static String RPC_NAME = "InvoiceService.validateCreditCard";

    public PaymentServiceValidateCustomerCreditCardOperation(Integer creditCardId){
        super(RPC_NAME, creditCardId);
    }

    @Override
    public CreditCardValidationResults parseResult(Object rawResult) throws InfusionsoftAuthorizationFailureException, InfusionsoftResponseParsingException {
        final Map<String, String> struct = (Map<String, String>) rawResult;
        return new CreditCardValidationResults(struct);
    }

}
