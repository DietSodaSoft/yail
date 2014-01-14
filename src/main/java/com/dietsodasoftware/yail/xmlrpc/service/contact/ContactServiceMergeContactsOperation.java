package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#merge
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 8:56 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "merge")
public class ContactServiceMergeContactsOperation extends SimpleRpcServiceOperation<Boolean> {

    public ContactServiceMergeContactsOperation(Integer contactId, Integer duplicateContactId){
        super(contactId, duplicateContactId);
    }

}
