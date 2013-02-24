package com.dietsodasoftware.isft.xmlrpc.service.data;

import com.dietsodasoftware.isft.xmlrpc.model.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/24/13
 * Time: 12:13 AM
 */
public class DataServiceLoadOperation<T extends Model> extends DataServiceBase<DataServiceLoadOperation<T>, T> {
    private static final String RPC_NAME = "DataService.load";

    private final int modelId;

    public DataServiceLoadOperation(Class<T> clazz, int modelId) {
        super(clazz);

        this.modelId = modelId;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List params = new LinkedList();
        params.add(getTableName());
        params.add(modelId);
        params.add(getReturnFieldNames());

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }
}
