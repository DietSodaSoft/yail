package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#resumeCampaignForContact
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:12 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "resumeCampaignForContact")
public class ContactServiceResumeFollowUpSequenceOperation extends SimpleRpcServiceOperation<Boolean> {
    public ContactServiceResumeFollowUpSequenceOperation(Integer contactId, Integer followUpSequenceId){
        super(contactId, followUpSequenceId);
    }
}
