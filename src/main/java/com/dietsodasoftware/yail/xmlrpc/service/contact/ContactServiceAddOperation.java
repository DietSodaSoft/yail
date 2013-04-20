package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Adds a contact unconditionally, or adds a contact de-duping against email, email and name, or email name and company.
 */
@InfusionsoftRpc(service = "ContactService", method = "add")
public class ContactServiceAddOperation extends SimpleRpcServiceOperation<Integer> {

    public enum DupCheckType{
        Email,
        EmailAndName,
        EmailAndNameAndCompany
    }

    public ContactServiceAddOperation(Contact contact) {
        super(contact.getStruct());
    }

    // Cheating here.  The annotated service/method are ignored using this constructor.
    public ContactServiceAddOperation(Contact contact, DupCheckType dupType){
        super("ContactService", "addWithDupCheck");

        getOperationParameters().add(contact.getStruct());
        getOperationParameters().add(dupType.name());
    }

}
