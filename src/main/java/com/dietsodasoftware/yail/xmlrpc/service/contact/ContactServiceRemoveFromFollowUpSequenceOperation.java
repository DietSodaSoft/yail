package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#removeFromCampaign
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:10 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "removeFromCampaign")
public class ContactServiceRemoveFromFollowUpSequenceOperation extends SimpleRpcServiceOperation<Boolean> {
    public ContactServiceRemoveFromFollowUpSequenceOperation(Integer contactId, Integer followUpSequenceId){
        super(contactId, followUpSequenceId);
    }
}
