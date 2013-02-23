package com.dietsodasoftware.isft.xmlrpc.service.data;

import java.util.LinkedList;
import java.util.List;

import com.dietsodasoftware.isft.xmlrpc.model.Model;
import com.dietsodasoftware.isft.xmlrpc.model.NamedField;

public class DataServiceFindByFieldOperation<MT extends Model> extends DataServiceBase<DataServiceFindByFieldOperation<MT>, MT> {
	
	private final static String RPC_NAME = "DataService.findByField";

	private String fieldName;
	private Object fieldValue;
	
	public DataServiceFindByFieldOperation(Class<MT> clazz){
		super(clazz);
	}

	public DataServiceFindByFieldOperation<MT> setFieldName(NamedField fieldName){
		return setFieldName(fieldName.name());
	}

	private DataServiceFindByFieldOperation<MT> setFieldName(String fieldName){
		this.fieldName = fieldName;
		return this;
	}
	
	public DataServiceFindByFieldOperation<MT> setCustomFieldName(String fieldName){
		this.fieldName = "_" + fieldName;
		return this;
	}
	
	public DataServiceFindByFieldOperation<MT> setFieldValue(Object fieldValue){
		this.fieldValue = fieldValue;
		return this;
	}
	
	public DataServiceFindByFieldOperation<MT> nextPage(){
		return new DataServiceFindByFieldOperation<MT>(getModelTypeClass())
		              .setFieldName(fieldName)
		              .setFieldValue(fieldValue)
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
		              .setFieldName(fieldName)
		              .setFieldValue(fieldValue)
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
