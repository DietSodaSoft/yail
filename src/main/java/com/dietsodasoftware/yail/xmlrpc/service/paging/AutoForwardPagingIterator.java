package com.dietsodasoftware.yail.xmlrpc.service.paging;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.model.ContactAction;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionResults;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/9/13
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoForwardPagingIterator<MT extends Model, RT  extends InfusionsoftModelCollectionOperation<?, MT>> implements Iterable<MT> {
    private final YailClient client;
    private final ForwardPagingRequest<MT, RT> seedRequest;
    private final ForwardPagingBound<MT> boundary;
    public AutoForwardPagingIterator(YailClient client, ForwardPagingRequest<MT, RT> seedRequest) {
        this(client, seedRequest, null);
    }

    public AutoForwardPagingIterator(YailClient client, ForwardPagingRequest<MT, RT> seedRequest, ForwardPagingBound<MT> boundary) {
        this.seedRequest = seedRequest;
        this.client = client;
        this.boundary = boundary;
    }
        @Override
    public Iterator<MT> iterator() {
        return new PagingIterator<MT, RT>(client, seedRequest, boundary);
    }

    private static class PagingIterator<IMT extends Model, IRT extends InfusionsoftModelCollectionOperation<?, IMT>>
                                 implements Iterator<IMT> {

        private PagingIterator(YailClient client, ForwardPagingRequest<IMT, IRT> seedRequest, ForwardPagingBound<IMT> boundary){
            this.client = client;
            this.currentRequest = seedRequest;
            if(boundary == null){
                boundary = new ForwardPagingBound<IMT>() {
                    @Override
                    public boolean isInBounds(IMT model) {
                        return true;
                    }
                };
            }
            this.boundary = boundary;
        }

        private final YailClient client;
        private final ForwardPagingBound<IMT> boundary;

        private ForwardPagingRequest<IMT, IRT> currentRequest; // = seedRequest;
        private Iterator<IMT> currentResultsIterator;
        private int currentResultsLength;
        private IMT lookahead;

        @Override
        public boolean hasNext() {
            // if we don't have a current results, get one
            // if our current results is null => false
            // else if our current results is not null,
            //      if results.hasNext() => true
            //      else get next page results => new results.hasNext()

            InfusionsoftModelCollectionResults<IMT> results = null;

            if(currentResultsIterator == null){
                try {
                    results = client.call(currentRequest.getRequest());
                    currentResultsIterator = results.iterator();
                    currentResultsLength = results.length();
                } catch (InfusionsoftXmlRpcException e) {
                    throw new RuntimeException("Unable to fetch Infusionsoft result set", e);
                }
            }

            if(currentResultsIterator == null){
                return false;
            }



            if(currentResultsIterator.hasNext()){
                lookahead = currentResultsIterator.next();
                return boundary.isInBounds(lookahead);
            } else {
                // at the end of the current result set.  try to get the next page.
                if(currentResultsLength < currentRequest.getRequest().getLimit()){
                    // the last result set is smaller than the size we asked for -
                    // we reached the end of the result set.
                    return false;
                } else {
                    currentResultsIterator = null;
                    currentRequest = currentRequest.nextPage();
                    return hasNext();
                }
            }
        }


        @Override
        public IMT next() {
            return lookahead;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This is a read-only web service helper");
        }
    }
}
