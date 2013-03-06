package com.dietsodasoftware.isft.xmlrpc.service.product;

import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 11:36 PM
 */
public class ProductServiceIncreaseInventoryOperation extends InfusionsoftXmlRpcServiceOperation<Boolean> {
    private final String RPC_NAME = "ProductService.increaseInventory";

    private final int productId;
    private final int quantity;

    public ProductServiceIncreaseInventoryOperation(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> params = new LinkedList<Object>();
        params.add(productId);
        params.add(quantity);

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public Boolean parseResult(Object rawResponse) throws InfusionsoftResponseParsingException {
        return (Boolean)rawResponse;
    }
}
