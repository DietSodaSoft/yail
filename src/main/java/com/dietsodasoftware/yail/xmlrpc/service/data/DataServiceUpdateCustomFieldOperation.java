package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.CustomField;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 8:21 PM
 */
@InfusionsoftRpc(service = "DataService", method = "updateCustomField")
public class DataServiceUpdateCustomFieldOperation extends SimpleRpcServiceOperation<Boolean> {

    public DataServiceUpdateCustomFieldOperation(Integer customFieldId, CustomField prototype){
        super(customFieldId, prototype.getStruct());
    }

}
