package com.dietsodasoftware.isft.xmlrpc.service.data;

import com.dietsodasoftware.isft.xmlrpc.model.Model;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftModelCollectionOperation;

public abstract class DataServiceBase<BT, MT extends Model> extends InfusionsoftModelCollectionOperation<BT, MT> {

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


}
