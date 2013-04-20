package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Handles contact creation
 */
@InfusionsoftRpc(service = "ContactService", method = "add")
public class ContactServiceAddOperation extends SimpleRpcServiceOperation<Integer> {

    public ContactServiceAddOperation(Contact contact) {
        super(contact.getStruct());
    }

}
