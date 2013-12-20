package com.dietsodasoftware.yail.xmlrpc.service.paging;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;

import javax.xml.ws.soap.MTOM;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/9/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ForwardPagingRequest<MT extends Model, RT extends InfusionsoftModelCollectionOperation<?, MT>> {

    public ForwardPagingRequest<MT, RT> nextPage();

    public RT getRequest();
}
