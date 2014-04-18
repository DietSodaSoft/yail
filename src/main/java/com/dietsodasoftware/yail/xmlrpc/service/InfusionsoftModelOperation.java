package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.utils.ModelUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/23/13
 * Time: 11:58 AM
 */
public abstract class InfusionsoftModelOperation<MT extends Model, RT> extends InfusionsoftXmlRpcServiceOperation<RT>  {
    private final String tableName;
    private final Class<MT> modelTypeClass;
    private final Class<RT> returnTypeClass;

    private final static String [] customFieldScrubCharacters = new String[]{
            " ", ",", "'"
    };

    protected InfusionsoftModelOperation(Class<MT> modelTypeClass){
        this(modelTypeClass, null);
    }


    protected InfusionsoftModelOperation(Class<MT> modelTypeClass, Class<RT> returnTypeClass){

        this.modelTypeClass = modelTypeClass;
        this.returnTypeClass = returnTypeClass;
        this.tableName = Model.getTableNameForModel(modelTypeClass);

    }

    protected Class<MT> getModelTypeClass(){
        return modelTypeClass;
    }

    public String getTableName(){
        return tableName;
    }

    @Override
    public RT parseResult(Object rawResponse) {
        if(returnTypeClass.isAssignableFrom(Model.class) && rawResponse instanceof Map){
            return (RT) createModelInstance((Map<String, Object>) rawResponse);
        }
        return (RT)rawResponse;
    }

    protected <T extends NamedField> Collection<T> getAllModelFieldNames(){
        return createModelInstance(new HashMap()).allFields();
    }

    protected Collection<String> getAllModelReturnFieldNames(){
        return createModelInstance(new HashMap()).allFieldsNames();
    }

    protected MT createModelInstance(Map<String, Object> modelData){
        return ModelUtils.newInstance(getModelTypeClass(), modelData);
    }


    protected String scrubCustomFieldName(String customField){
        if(customField == null){
            return null;
        }

        for(String scrub: customFieldScrubCharacters){
            customField = customField.replaceAll(scrub, "");
        }
        return customField;
    }

}
