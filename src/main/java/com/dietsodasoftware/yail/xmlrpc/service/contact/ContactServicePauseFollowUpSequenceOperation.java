package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#pauseCampaign
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:04 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "pauseCampaign")
public class ContactServicePauseFollowUpSequenceOperation extends SimpleRpcServiceOperation<Boolean> {
    public ContactServicePauseFollowUpSequenceOperation(Integer contactId, Integer followUpSequenceId){
        super(contactId, followUpSequenceId);
    }
}
