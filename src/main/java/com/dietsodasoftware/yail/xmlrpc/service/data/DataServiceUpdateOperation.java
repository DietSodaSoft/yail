package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

@InfusionsoftRpc(service = "DataService", method = "update")
public class DataServiceUpdateOperation extends SimpleRpcServiceOperation<Integer> {

    public DataServiceUpdateOperation(Integer modelId, Model prototype){
        super(Model.getTableNameForModel(prototype.getClass()), modelId,  prototype.getStruct());
    }

}
