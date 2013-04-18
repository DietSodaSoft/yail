package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.model.ContactAction;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;
import com.dietsodasoftware.yail.xmlrpc.service.paging.AutoForwardPagingIterator;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingBound;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingRequest;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/9/13
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataServiceUtils {

    public static <MT extends Model> Iterator<MT> dataServiceQueryOperationForwardIteratorFor(YailClient client, DataServiceQueryOperation<MT> request){
        final AutoForwardPagingIterator<MT, DataServiceQueryOperation<MT>> pager = new AutoForwardPagingIterator(client, request);
        return pager.iterator();
    }

    public static <MT extends Model> Iterator<MT> dataServiceQueryOperationForwardIteratorWithBoundaryFor(YailClient client, DataServiceQueryOperation<MT> request, ForwardPagingBound<MT> boundary){
        final AutoForwardPagingIterator<MT, DataServiceQueryOperation<MT>> pager = new AutoForwardPagingIterator(client, request, boundary);
        return pager.iterator();
    }


}
