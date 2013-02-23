package com.dietsodasoftware.isft.xmlrpc.service;

import com.dietsodasoftware.isft.xmlrpc.model.NamedField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class InfusionsoftFieldResultsOperation<BT, MT> extends InfusionsoftModelOperation<MT, InfusionsoftFieldResults<MT>>{

	private final List<String> returnFieldNames;

	protected InfusionsoftFieldResultsOperation(Class<MT> modelTypeClass){
        super(modelTypeClass);

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
	
	@Override
	public InfusionsoftFieldResults<MT> parseResult(Object rawResponse) {
		return new InfusionsoftFieldResults<MT>(getModelTypeClass(), (Object[]) rawResponse);
	}



}
