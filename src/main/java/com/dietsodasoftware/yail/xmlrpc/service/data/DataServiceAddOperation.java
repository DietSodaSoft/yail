package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/23/13
 * Time: 11:48 AM
 */
public class DataServiceAddOperation<MT extends Model> extends InfusionsoftModelOperation<MT, Integer> {

    private static final String RPC_NAME = "DataService.add";

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
        final List params = new LinkedList();
        params.add(getTableName());
        params.add(model.getStruct());

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }
}
