package com.dietsodasoftware.yail.xmlrpc.service.product;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 11:52 PM
 */
@InfusionsoftRpc(service = "ProductService", method = "decreaseInventory")
public class ProductServiceDecreaseInventoryOperation extends SimpleRpcServiceOperation<Boolean> {

    public ProductServiceDecreaseInventoryOperation(int productId, int quantity) {
        super(productId, quantity);
    }

}
