package com.dietsodasoftware.isft.xmlrpc.service;

import com.dietsodasoftware.isft.xmlrpc.model.Model;
import com.dietsodasoftware.isft.xmlrpc.model.NamedField;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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


    protected InfusionsoftModelOperation(Class<MT> modelTypeClass){

        this.modelTypeClass = modelTypeClass;
        this.tableName = Model.getTableNameForModel(modelTypeClass);

    }

    protected Class<MT> getModelTypeClass(){
        return modelTypeClass;
    }

    @SuppressWarnings("rawtypes")
    public List emptyParameters(){
        return new LinkedList();
    }

    public String getTableName(){
        return tableName;
    }

    @Override
    public RT parseResult(Object rawResponse) {
        return (RT)rawResponse;
    }

    protected <T extends NamedField> Collection<T> getAllModelFieldNames(){
        return createModelInstance(new HashMap()).allFields();
    }

    protected Collection<String> getAllModelReturnFieldNames(){
        return createModelInstance(new HashMap()).allFieldsNames();
    }

    protected MT createModelInstance(Map<String, Object> modelData){
        try {
            return Model.getModelMapConstructor(getModelTypeClass()).newInstance(modelData);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unable to determine named fields for Model " + getModelTypeClass().getName() + ".", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to determine named fields for Model " + getModelTypeClass().getName() + ".", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to determine named fields for Model " + getModelTypeClass().getName() + ".", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to determine named fields for Model " + getModelTypeClass().getName() + ".", e);
        }
    }


}
