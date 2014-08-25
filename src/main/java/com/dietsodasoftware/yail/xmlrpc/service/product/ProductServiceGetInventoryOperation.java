package com.dietsodasoftware.yail.xmlrpc.service.product;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 11:55 PM
 */
@InfusionsoftRpc(service = "ProductService", method = "getInventory")
public class ProductServiceGetInventoryOperation extends SimpleRpcServiceOperation<Integer> {

    public ProductServiceGetInventoryOperation(int productId) {
        super(productId);
    }


}
