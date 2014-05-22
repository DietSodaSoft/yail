package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.ArgumentValidator;
import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftParameterValidationException;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingRequest;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@InfusionsoftRpc(service = "DataService", method = "query")
public class DataServiceQueryOperation<MT extends Model> extends InfusionsoftModelCollectionOperation<DataServiceQueryOperation<MT>,MT>
implements ForwardPagingRequest<MT, DataServiceQueryOperation<MT>> {

	protected DataServiceQueryOperation(DataServiceQueryFilter<MT> queryFilter) {
		super(queryFilter.getModelClass());

        this.queryFilter = queryFilter;
	}

    private final DataServiceQueryFilter queryFilter;

	private Boolean ascending;
	private String orderBy;

    @ArgumentValidator
    public void ensureOrderByAscending() throws InfusionsoftParameterValidationException {
        if(ascending != null && orderBy == null){
            throw new InfusionsoftParameterValidationException("Must provide orderBy field or custom field to declare ascending/descending query");
        }

        if(ascending == null && orderBy != null){
            throw new InfusionsoftParameterValidationException("Must provide ascending/descending to declare order by field or custom field query");
        }

    }

    public DataServiceQueryCountOperation<MT> createQueryCountOperation(){
        return new DataServiceQueryCountOperation<MT>(queryFilter);
    }

    public DataServiceQueryOperation<MT> orderBy(NamedField field){
        this.orderBy = field.name();
        return this;
    }

    public DataServiceQueryOperation<MT> orderByCustomField(String field){
        this.orderBy = "_" + Model.scrubCustomFieldName(field);
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
        final DataServiceQueryOperation<MT> op = new DataServiceQueryOperation<MT>(queryFilter)
                .setLimit(getLimit())
                .setPage(getPage() + 1)
                .setReturnFieldNames(getReturnFieldNames());

        op.orderBy = orderBy;
        op.ascending = ascending;

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
        final DataServiceQueryOperation<MT> op = new DataServiceQueryOperation<MT>(queryFilter)
                .setLimit(getLimit())
                .setPage(page)
                .setReturnFieldNames(getReturnFieldNames());

        op.orderBy = orderBy;
        op.ascending = ascending;

        return op;
    }



    @SuppressWarnings("unchecked")
	@Override
	protected List<?> getOperationParameters() {
        final List list = ListFactory.quickLinkedList(
                getTableName(),
                getLimit(),
                getPage(),
                queryFilter.getFilterParameters(),
                getReturnFieldNames()
        );

        if(orderBy != null){
        	list.add(orderBy);
        }
        
        if(ascending != null){
        	list.add(ascending);
        }
        
        return list;
	}

}
