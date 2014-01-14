package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#rescheduleCampaignStep
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:14 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "rescheduleCampaignStep")
public class ContactServiceRescheduleFollowUpSequenceStepOperation extends SimpleRpcServiceOperation<Boolean> {
    public ContactServiceRescheduleFollowUpSequenceStepOperation(Integer contactId, Integer followUpSequenceId){
        super(contactId, followUpSequenceId);
    }
}
