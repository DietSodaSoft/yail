package com.dietsodasoftware.isft.xmlrpc.service;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;
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
        if( modelTypeClass.isAnnotationPresent(TableName.class)){
            final TableName tableAnnotation = modelTypeClass.getAnnotation(TableName.class);
            tableName = tableAnnotation.table();
        } else {
            throw new IllegalArgumentException("Must provide the return type Model class annotated by @TableName on class " + modelTypeClass.getName());
        }

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
        return createEmptyModel().allFields();
    }

    protected Collection<String> getAllModelReturnFieldNames(){
        return createEmptyModel().allFieldsNames();
    }

    private MT createEmptyModel(){
        try {
            @SuppressWarnings("rawtypes")
            final Map<?,?> emptyModel = new HashMap();
            return Model.getModelMapConstructor(getModelTypeClass()).newInstance(emptyModel);
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
