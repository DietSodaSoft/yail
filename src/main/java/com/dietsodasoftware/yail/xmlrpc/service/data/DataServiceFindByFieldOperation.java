package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;

import java.util.LinkedList;
import java.util.List;

public class DataServiceFindByFieldOperation<MT extends Model> extends InfusionsoftModelCollectionOperation<DataServiceFindByFieldOperation<MT>,MT> {
	
	private final static String RPC_NAME = "DataService.findByField";

	private String fieldName;
	private Object fieldValue;
	
	public DataServiceFindByFieldOperation(Class<MT> clazz){
		super(clazz);
	}

	public DataServiceFindByFieldOperation<MT> setFieldCriteria(NamedField fieldName, Object fieldValue){
		return setFieldCriteria(fieldName.name(), fieldValue);
	}

	private DataServiceFindByFieldOperation<MT> setFieldCriteria(String fieldName, Object fieldValue){
		this.fieldName = fieldName;
        this.fieldValue = fieldValue;

		return this;
	}
	
	public DataServiceFindByFieldOperation<MT> setCustomFieldCriteria(String fieldName, Object fieldValue){
        return setFieldCriteria("_" + fieldName, fieldValue);
	}
	
	public DataServiceFindByFieldOperation<MT> nextPage(){
		return new DataServiceFindByFieldOperation<MT>(getModelTypeClass())
		              .setFieldCriteria(fieldName, fieldValue)
		              .setLimit(getLimit())
		              .setPage(getPage() + 1)
		              .setReturnFieldNames(getReturnFieldNames());
	}
	
	public DataServiceFindByFieldOperation<MT> previousPage(){
		int page = getPage();
		if(page > 0){
			page--;
		} else {
			page = 0;
		}
		return new DataServiceFindByFieldOperation<MT>(getModelTypeClass())
                      .setFieldCriteria(fieldName, fieldValue)
                      .setLimit(getLimit())
		              .setPage(page)
		              .setReturnFieldNames(getReturnFieldNames());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getOperationParameters() {
		@SuppressWarnings("rawtypes")
		final List params = new LinkedList();
		params.add(getTableName());
		params.add(getLimit());
		params.add(getPage());
		params.add(fieldName);
		params.add(fieldValue);
		params.add(getReturnFieldNames());
		
		return params;
	}

	@Override
	public String getRpcName() {
		return RPC_NAME;
	}

}
