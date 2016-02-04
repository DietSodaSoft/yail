package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 11:38 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "removeFromGroup")
public class ContactServiceRemoveContactTagOperation extends SimpleRpcServiceOperation<Boolean> {

    public ContactServiceRemoveContactTagOperation(Integer contactId, Integer tagId){
        super(contactId, tagId);
    }

}
