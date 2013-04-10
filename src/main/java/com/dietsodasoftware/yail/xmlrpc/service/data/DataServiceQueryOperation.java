package com.dietsodasoftware.yail.xmlrpc.service.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingRequest;

public class DataServiceQueryOperation<MT extends Model> extends InfusionsoftModelCollectionOperation<DataServiceQueryOperation<MT>,MT>
implements ForwardPagingRequest<MT, DataServiceQueryOperation<MT>> {

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

    public enum Compare{
        gt("~>~"),
        lt("~<~"),
        gte("~>=~"),
        lte("~<=~")
        ;

        private final String compareString;
        private Compare(String compareString){
            this.compareString = compareString;
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
	
	private DataServiceQueryOperation<MT> fieldIsNull(String fieldName){
		parameterValues.put(fieldName, NULL);
		return this;
	}
	
	private DataServiceQueryOperation<MT> fieldLike(String fieldName, String value, Like like){
		parameterValues.put(fieldName, like.getParameterValue(value));
		return this;
	}

    public DataServiceQueryOperation<MT> fieldEquals(NamedField field, Object value){
        return fieldEquals(field.name(), value);
    }

    public DataServiceQueryOperation<MT> fieldIsNull(NamedField field){
        return fieldIsNull(field.name());
    }

    public DataServiceQueryOperation<MT> fieldLike(NamedField field, String value, Like like){
        return fieldLike(field.name(), value, like);
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

    public DataServiceQueryOperation<MT> fieldCompare(NamedField field, Compare compare, String value){
        fieldEquals(field.name(), compare.compareString + value);
        return this;
    }

    public DataServiceQueryOperation<MT> customFieldCompare(String field, Compare compare, String value){
        fieldEquals("_" + field, compare.compareString + value);
        return this;
    }

    public DataServiceQueryOperation<MT> orderBy(NamedField field){
        this.orderBy = field.name();
        return this;
    }

    public DataServiceQueryOperation<MT> orderByCustomField(String field){
        this.orderBy = "_" + field;
        return this;
    }

    public DataServiceQueryOperation<MT> ascending(){
        this.ascending = true;
        return this;
    }

    public DataServiceQueryOperation<MT> descending(){
        this.ascending = false;
        return this;
    }


    public DataServiceQueryOperation<MT> nextPage(){
        final DataServiceQueryOperation<MT> op = new DataServiceQueryOperation<MT>(getModelTypeClass())
                .setLimit(getLimit())
                .setPage(getPage() + 1)
                .setReturnFieldNames(getReturnFieldNames());

        op.orderBy = orderBy;
        op.ascending = ascending;
        op.parameterValues.putAll(parameterValues);

        return op;
    }

    @Override
    public DataServiceQueryOperation<MT> getRequest() {
        return this;
    }

    public DataServiceQueryOperation<MT> previousPage(){
        int page = getPage();
        if(page > 0){
            page--;
        } else {
            page = 0;
        }
        final DataServiceQueryOperation<MT> op = new DataServiceQueryOperation<MT>(getModelTypeClass())
                .setLimit(getLimit())
                .setPage(page)
                .setReturnFieldNames(getReturnFieldNames());

        op.orderBy = orderBy;
        op.ascending = ascending;
        op.parameterValues.putAll(parameterValues);

        return op;
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
