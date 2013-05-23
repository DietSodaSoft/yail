package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#addWithDupCheck
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:17 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "addWithDupCheck")
public class ContactServiceAddWithDuplicateCheckOperation extends SimpleRpcServiceOperation<Integer> {
    public enum DuplicateCheckStrategy {
        Email, EmailAndName, EmailAndNameAndCompany;
    }

    public ContactServiceAddWithDuplicateCheckOperation(Contact prototype, DuplicateCheckStrategy strategy){
        super(prototype.getStruct(), strategy.name());
    }
}
