package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * User: wendel.schultz
 * Date: 4/20/14
 */
@InfusionsoftRpc(service = "DataService", method = "count")
public class DataServiceQueryCountOperation<MT extends Model> extends InfusionsoftModelOperation<MT, Integer> {

    private final DataServiceQueryFilter<MT> queryFilter;

    public DataServiceQueryCountOperation(DataServiceQueryFilter<MT> queryFilter) {
        super(queryFilter.getModelClass());

        this.queryFilter = queryFilter;
    }

    public DataServiceQueryOperation<MT> createQueryOperation(){
        return new DataServiceQueryOperation<MT>(queryFilter);
    }

    @Override
    protected List<?> getOperationParameters() {
        return ListFactory.quickLinkedList(
                getTableName(),
                queryFilter.getFilterParameters()
        );
    }
}
