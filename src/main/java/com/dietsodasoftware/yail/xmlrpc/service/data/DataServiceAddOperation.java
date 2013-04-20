package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/23/13
 * Time: 11:48 AM
 */
@InfusionsoftRpc(service = "DataService", method = "add")
public class DataServiceAddOperation<MT extends Model> extends InfusionsoftModelOperation<MT, Integer> {

    private MT model;

    public DataServiceAddOperation(Class<MT> modelTypeClass) {
        super(modelTypeClass);
    }

    public DataServiceAddOperation<MT> useModelPrototype(MT prototype){
        model = prototype;
        return this;
    }

    @Override
    protected List<?> getOperationParameters() {
        return ListFactory.quickUnmodifiableLinkedList(getTableName(), model.getStruct());
    }

}
