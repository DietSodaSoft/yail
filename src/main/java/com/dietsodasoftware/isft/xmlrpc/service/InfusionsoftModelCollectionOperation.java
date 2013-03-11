package com.dietsodasoftware.isft.xmlrpc.service;

import com.dietsodasoftware.isft.xmlrpc.model.Model;
import com.dietsodasoftware.isft.xmlrpc.model.NamedField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class InfusionsoftModelCollectionOperation<BT, MT extends Model> extends InfusionsoftModelOperation<MT, InfusionsoftModelCollectionResults<MT>>{

    public static final int FIRST_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_LIMIT_SIZE = 100;

    private int limit = DEFAULT_PAGE_LIMIT_SIZE;
    private int page = FIRST_PAGE_NUMBER;

    private final List<String> returnFieldNames;

	protected InfusionsoftModelCollectionOperation(Class<MT> modelTypeClass){
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

	/** for paging, not public use */
	@SuppressWarnings("unchecked")
	protected BT setReturnFieldNames(Collection<String> fieldNames){
		this.returnFieldNames.clear();
		this.returnFieldNames.addAll(fieldNames);
		return (BT)this;
	}
	
	@Override
	public InfusionsoftModelCollectionResults<MT> parseResult(Object rawResponse) {
		return new InfusionsoftModelCollectionResults<MT>(getModelTypeClass(), (Object[]) rawResponse);
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

    public Collection<String> getReturnFieldNames(){
        if(returnFieldNames.isEmpty()){
            return getAllModelReturnFieldNames();
        }
        return returnFieldNames;
    }


}
