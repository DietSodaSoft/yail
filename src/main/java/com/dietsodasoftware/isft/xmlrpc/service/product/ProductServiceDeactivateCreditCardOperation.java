package com.dietsodasoftware.isft.xmlrpc.service.product;

import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 11:32 PM
 */
public class ProductServiceDeactivateCreditCardOperation extends InfusionsoftXmlRpcServiceOperation<Boolean> {
    private static final String RPC_NAME = "ProductService.deactivateCreditCard";

    private final Integer ccId;

    public ProductServiceDeactivateCreditCardOperation(Integer ccId) {
        this.ccId = ccId;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> params = new LinkedList<Object>();
        params.add(ccId);

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public Boolean parseResult(Object rawResponse) throws InfusionsoftResponseParsingException {
        return (Boolean) rawResponse;
    }
}
