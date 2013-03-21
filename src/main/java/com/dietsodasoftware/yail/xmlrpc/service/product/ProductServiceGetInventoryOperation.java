package com.dietsodasoftware.yail.xmlrpc.service.product;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 11:55 PM
 */
public class ProductServiceGetInventoryOperation extends InfusionsoftXmlRpcServiceOperation<Integer> {
    private static final String RPC_NAME = "ProductService.getInventory";

    private final int productId;

    public ProductServiceGetInventoryOperation(int productId) {
        this.productId = productId;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> params = new LinkedList<Object>();
        params.add(productId);

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public Integer parseResult(Object rawResponse) throws InfusionsoftResponseParsingException {
        return (Integer)rawResponse;
    }
}
