package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class InfusionsoftModelCollectionOperation<BT, MT extends Model> extends InfusionsoftModelOperation<MT, InfusionsoftModelCollectionResults<MT>>{

    public static final int FIRST_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_LIMIT_SIZE = 100;

    private final static String [] customFieldScrubCharacters = new String[]{
            " ", ",", "'"
    };

    private int limit = DEFAULT_PAGE_LIMIT_SIZE;
    private int page = FIRST_PAGE_NUMBER;

    private final List<String> returnFieldNames;

	protected InfusionsoftModelCollectionOperation(Class<MT> modelTypeClass){
        super(modelTypeClass);

		returnFieldNames = new LinkedList<String>();
	}
	
	@SuppressWarnings("unchecked")
	protected BT addReturnFieldName(String fieldName){
		returnFieldNames.add(fieldName);
		return (BT)this;
	}
	
	public BT addReturnFieldName(NamedField field){
		return addReturnFieldName(field.name());
	}
	
	public BT addCustomReturnFieldName(String customFieldName){
		return addReturnFieldName("_" + scrubCustomFieldName(customFieldName));
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
        if(limit < 1){
            throw new IllegalArgumentException("Invalid argument: limit");
        }

        this.limit = limit;
        return (BT)this;
    }

    public int getLimit(){
        return limit;
    }

    @SuppressWarnings("unchecked")
    public BT setPage(int page){
        if(page < 0){
            throw new IllegalArgumentException("Invalid argument: limit");
        }

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

    protected static String scrubCustomFieldName(String customField){
        if(customField == null){
            return null;
        }

        for(String scrub: customFieldScrubCharacters){
            customField = customField.replaceAll(scrub, "");
        }
        return customField;
    }


}
