package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 8:58 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "addToCampaign")
public class ContactServiceAddToFollowUpSequenceOperation extends SimpleRpcServiceOperation<Boolean> {

    public ContactServiceAddToFollowUpSequenceOperation(Integer contactId, Integer campaignId){
        super(contactId, campaignId);
    }
}
