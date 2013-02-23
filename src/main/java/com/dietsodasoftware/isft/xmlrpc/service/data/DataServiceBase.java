package com.dietsodasoftware.isft.xmlrpc.service.data;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.dietsodasoftware.isft.xmlrpc.model.Model;
import com.dietsodasoftware.isft.xmlrpc.model.NamedField;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftFieldResultsOperation;

public abstract class DataServiceBase<BT, MT extends Model> extends InfusionsoftFieldResultsOperation<BT, MT> {

	public static final int FIRST_PAGE_NUMBER = 0;
	public static final int DEFAULT_PAGE_LIMIT_SIZE = 100;
	
	private int limit = DEFAULT_PAGE_LIMIT_SIZE;
	private int page = FIRST_PAGE_NUMBER;
	
	public DataServiceBase(Class<MT> clazz){
		super(clazz);
	}

	@SuppressWarnings("unchecked")
	public BT setLimit(int limit){
		this.limit = limit;
		return (BT)this;
	}
	
	public int getLimit(){
		return limit;
	}
	
	@SuppressWarnings("unchecked")
	public BT setPage(int page){
		this.page = page;
		return (BT)this;
	}
	
	public int getPage(){
		return page;
	}

	@Override
	public Collection<String> getReturnFieldNames(){
		final Collection<String> returnFieldNames = super.getReturnFieldNames();
		if(returnFieldNames.isEmpty()){
			final Collection<NamedField> fieldNames = getAllModelFieldNames();
			final Collection<String> fields = new LinkedList<String>(); 
			for(NamedField name: fieldNames){
				fields.add(name.name());
			}
			
			return fields;
		}
		return returnFieldNames;
	}
	
	protected <T extends NamedField> Collection<T> getAllModelFieldNames(){
		try {
			@SuppressWarnings("rawtypes")
			final Map<?,?> emptyModel = new HashMap();
			return parseResult(null).getModelConstructor().newInstance(emptyModel).allFields();
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
