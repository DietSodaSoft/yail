package com.dietsodasoftware.isft.xmlrpc.service.data;

import com.dietsodasoftware.isft.xmlrpc.model.Model;
import com.dietsodasoftware.isft.xmlrpc.model.NamedField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataServiceQueryOperation<MT extends Model> extends DataServicePagedModelCollectionOperationBase<DataServiceQueryOperation<MT>, MT> {

	public DataServiceQueryOperation(Class<MT> clazz) {
		super(clazz);
	}

	private final static String RPC_NAME = "DataService.query";
	public enum Like{
		before("%%%s"), after("%s%%"), surrounding("%%%s%%");
		
		private final String pattern;
		private Like(String pattern){
			this.pattern = pattern;
		}
		
		String getParameterValue(String compareTo){
			return String.format(pattern, compareTo);
		}
	}
	
	private static final String NULL = "~null~";
	
	// required params
	private final Map<String, Object> parameterValues = new HashMap<String, Object>();
	
	private Boolean ascending;
	private String orderBy;
	
	@Override
	public String getRpcName() {
        return RPC_NAME;
	}

    private DataServiceQueryOperation<MT> fieldEquals(String fieldName, Object value){
        parameterValues.put(fieldName, value);
        return this;
    }

    public DataServiceQueryOperation<MT> fieldEquals(NamedField<MT> field, Object value){
        parameterValues.put(field.name(), value);
        return this;
    }

    private DataServiceQueryOperation<MT> fieldIsNull(String fieldName){
        parameterValues.put(fieldName, NULL);
        return this;
    }

    public DataServiceQueryOperation<MT> fieldIsNull(NamedField<MT> field){
        parameterValues.put(field.name(), NULL);
        return this;
    }

    public DataServiceQueryOperation<MT> fieldLike(NamedField<MT> field, String value, Like like){
        return fieldLike(field.name(), value, like);
    }
	
	private DataServiceQueryOperation<MT> fieldLike(String fieldName, String value, Like like){
		parameterValues.put(fieldName, like.getParameterValue(value));
		return this;
	}

	public DataServiceQueryOperation<MT> customFieldEquals(String fieldName, Object value){
		return fieldEquals("_" + fieldName, value);
	}
	
	public DataServiceQueryOperation<MT> customFieldIsNull(String fieldName){
		return fieldIsNull("_" + fieldName);
	}
	
	public DataServiceQueryOperation<MT> customFieldLike(String fieldName, String value, Like like){
		return fieldLike("_" + fieldName, value, like);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getOperationParameters() {
		@SuppressWarnings("rawtypes")
        final List list = emptyParameters();
        list.add(getTableName());
        list.add(getLimit());
        list.add(getPage());
        list.add(parameterValues);
        list.add(getReturnFieldNames());
        
        if(orderBy != null){
        	list.add(orderBy);
        }
        
        if(ascending != null){
        	list.add(ascending);
        }
        
        return list;
	}

}
