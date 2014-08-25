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
 * Time: 12:30 PM
 */
@InfusionsoftRpc(service = "DataService", method = "delete")
public class DataServiceDeleteOperation<MT extends Model> extends InfusionsoftModelOperation<MT, Boolean> {

    private final Integer modelId;

    public DataServiceDeleteOperation(Class<MT> modelClass, Integer modelId) {
        super(modelClass);
        this.modelId = modelId;
    }

    @Override
    protected List<?> getOperationParameters() {
        return ListFactory.quickUnmodifiableLinkedList(getTableName(), modelId);
    }

}
