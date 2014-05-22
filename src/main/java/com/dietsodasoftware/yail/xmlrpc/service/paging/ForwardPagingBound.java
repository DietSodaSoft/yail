package com.dietsodasoftware.yail.xmlrpc.service.paging;

import com.dietsodasoftware.yail.xmlrpc.model.Model;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/9/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ForwardPagingBound<MT extends Model> {

    public boolean isInBounds(MT model);

}
