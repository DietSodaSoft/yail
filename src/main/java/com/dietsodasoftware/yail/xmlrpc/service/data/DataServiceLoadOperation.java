package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelOperation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/24/13
 * Time: 8:32 AM
 */
public class DataServiceLoadOperation<MT extends Model, RT> extends InfusionsoftModelOperation<MT, RT> {
    private static final String RPC_NAME = "DataService.load";

    private final int primaryKey;
    private final List<String> returnFields;

    public DataServiceLoadOperation(Class<MT> modelClass, int primaryKey) {
        super(modelClass);
        this.primaryKey = primaryKey;
        this.returnFields = new LinkedList<String>();
    }

    public DataServiceLoadOperation<MT, RT> addCustomReturnFieldName(String field){
        return addReturnFieldName("_" + field);
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
        final List fields = new LinkedList();
        final List params = new LinkedList();
        params.add(getTableName());
        params.add(Integer.valueOf(primaryKey));
        if(returnFields.isEmpty()){
            params.add(getAllModelReturnFieldNames());
        } else {
            params.add(returnFields);
        }

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public RT parseResult(Object rawResponse) {
        if(rawResponse == null){
            return null;
        }
        return (RT) createModelInstance((Map<String,Object>) rawResponse);
    }

}
