package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.customfields.OperationCustomField;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/24/13
 * Time: 8:32 AM
 */
@InfusionsoftRpc(service = "DataService", method = "load")
public class DataServiceLoadOperation<MT extends Model, RT> extends InfusionsoftModelOperation<MT, RT> {

    private final int primaryKey;
    private final List<String> returnFields;

    public DataServiceLoadOperation(Class<MT> modelClass, int primaryKey) {
        super(modelClass);
        this.primaryKey = primaryKey;
        this.returnFields = new LinkedList<String>();
    }

    public DataServiceLoadOperation<MT, RT> addReturnFieldName(OperationCustomField field){
        return addReturnFieldName(field.getApiArgument());
    }

    public DataServiceLoadOperation<MT, RT> addReturnFieldName(NamedField field){
        return addReturnFieldName(field.name());
    }

    private DataServiceLoadOperation<MT, RT> addReturnFieldName(String field){
        this.returnFields.add(field);
        return this;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List params = ListFactory.quickLinkedList(
                getTableName(),
                Integer.valueOf(primaryKey)
        );
        if(returnFields.isEmpty()){
            params.add(getAllModelReturnFieldNames());
        } else {
            params.add(returnFields);
        }

        return params;
    }

    @Override
    public RT parseResult(Object rawResponse) {
        if(rawResponse == null){
            return null;
        }
        return (RT) createModelInstance((Map<String,Object>) rawResponse);
    }

}
