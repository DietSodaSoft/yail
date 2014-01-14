package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Tags are sometimes known as "ContactGroups".  The tagId is the contact group ID.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:24 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "addToGroup")
public class ContactServiceAddContactTagOperation extends SimpleRpcServiceOperation<Boolean> {

    public ContactServiceAddContactTagOperation(Integer contactId, Integer tagId){
        super(contactId, tagId);
    }
}
