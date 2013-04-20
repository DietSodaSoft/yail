package com.dietsodasoftware.yail.xmlrpc.service.product;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 11:32 PM
 */
@InfusionsoftRpc(service = "ProductService", method = "deactivateCreditCard")
public class ProductServiceDeactivateCreditCardOperation extends SimpleRpcServiceOperation<Boolean> {

    public ProductServiceDeactivateCreditCardOperation(Integer ccId) {
        super(ccId);
    }

}
