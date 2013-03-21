package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/23/13
 * Time: 12:30 PM
 */
public class DataServiceDeleteOperation<MT extends Model> extends InfusionsoftModelOperation<MT, Boolean> {
    private final static String RPC_NAME = "DataService.delete";

    private final Integer modelId;

    public DataServiceDeleteOperation(Class<MT> modelClass, Integer modelId) {
        super(modelClass);
        this.modelId = modelId;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List params = new LinkedList();
        params.add(getTableName());
        params.add(modelId);

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }
}