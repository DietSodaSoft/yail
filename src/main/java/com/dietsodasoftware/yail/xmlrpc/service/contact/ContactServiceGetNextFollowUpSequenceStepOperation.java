package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#getNextCampaignStep
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:02 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "getNextCampaignStep")
public class ContactServiceGetNextFollowUpSequenceStepOperation extends SimpleRpcServiceOperation<Integer> {
    public ContactServiceGetNextFollowUpSequenceStepOperation(Integer contactId, Integer followUpSequenceId){
        super(contactId, followUpSequenceId);
    }
}
