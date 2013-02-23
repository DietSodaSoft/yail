package com.dietsodasoftware.isft.xmlrpc.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;
import com.dietsodasoftware.isft.xmlrpc.model.NamedField;

public abstract class InfusionsoftFieldResultsOperation<BT, MT> extends InfusionsoftXmlRpcServiceOperation<InfusionsoftFieldResults<MT>> {

	private final List<String> returnFieldNames;
	private final String tableName;
	private final Class<MT> modelTypeClass;


	protected InfusionsoftFieldResultsOperation(Class<MT> modelTypeClass){
		
		this.modelTypeClass = modelTypeClass;
		if( modelTypeClass.isAnnotationPresent(TableName.class)){
			final TableName tableAnnotation = modelTypeClass.getAnnotation(TableName.class);
			tableName = tableAnnotation.table(); 
		} else {
			throw new IllegalArgumentException("Must provide the return type Model class annotated by @TableName on class " + modelTypeClass.getName());
		}
		
		
		returnFieldNames = new LinkedList<String>();
	}
	
	@SuppressWarnings("unchecked")
	public BT addReturnFieldName(String fieldName){
		returnFieldNames.add(fieldName);
		return (BT)this;
	}
	
	public BT addReturnFieldName(NamedField field){
		return addReturnFieldName(field.name());
	}
	
	public BT addCustomReturnFieldName(String customFieldName){
		return addReturnFieldName("_" + customFieldName);
	}
	
	public Collection<String> getReturnFieldNames(){
		return returnFieldNames;
	}
	
	/** for paging, not public use */
	@SuppressWarnings("unchecked")
	protected BT setReturnFieldNames(Collection<String> fieldNames){
		this.returnFieldNames.clear();
		this.returnFieldNames.addAll(fieldNames);
		return (BT)this;
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
	public InfusionsoftFieldResults<MT> parseResult(Object rawResponse) {
		return new InfusionsoftFieldResults<MT>(modelTypeClass, (Object[]) rawResponse);
	}



}
