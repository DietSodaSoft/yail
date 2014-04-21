package com.dietsodasoftware.yail.xmlrpc.service.product;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 11:36 PM
 */
@InfusionsoftRpc(service = "ProductService", method = "increaseInventory")
public class ProductServiceIncreaseInventoryOperation extends SimpleRpcServiceOperation<Boolean> {

    public ProductServiceIncreaseInventoryOperation(int productId, int quantity) {
        super(productId, quantity);
    }

}
