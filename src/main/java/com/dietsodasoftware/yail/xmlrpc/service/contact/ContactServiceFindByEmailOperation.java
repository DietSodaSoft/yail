package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.List;

/**
 * Paging is ignored.  Limit and page number are irrelevant, though this service call does return a List of Contacts.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:55 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "findByEmail")
public class ContactServiceFindByEmailOperation extends InfusionsoftModelCollectionOperation<ContactServiceFindByEmailOperation, Contact> {

    private final String emailAddress;
    public ContactServiceFindByEmailOperation(String emailAddress){
        super(Contact.class);

        this.emailAddress = emailAddress;
    }

    @Override
    protected List<?> getOperationParameters() {
        return ListFactory.quickUnmodifiableLinkedList(
                emailAddress,
                getReturnFieldNames()
        );
    }
}
