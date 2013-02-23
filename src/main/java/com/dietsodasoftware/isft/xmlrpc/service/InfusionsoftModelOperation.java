package com.dietsodasoftware.isft.xmlrpc.service;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/23/13
 * Time: 11:58 AM
 */
public abstract class InfusionsoftModelOperation<MT, RT> extends InfusionsoftXmlRpcServiceOperation<RT>  {
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
}
