package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/20/13
 * Time: 12:01 AM
 */
@InfusionsoftRpc(service = "ContactService", method = "update")
public class ContactServiceUpdateContactOperation extends SimpleRpcServiceOperation<Integer> {

    public ContactServiceUpdateContactOperation(Integer contactId, Contact contact){
        super(contactId, contact.getStruct());
    }

}
