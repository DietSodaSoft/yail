package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * http://help.infusionsoft.com/api-docs/contactservice#runActionSequence
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:24 PM
 */
public class ContactServiceRunLegacyActionSetOperation extends SimpleRpcServiceOperation<LegacyActionSetResponse> {
    public ContactServiceRunLegacyActionSetOperation(Integer contactId, Integer actionSetId){
        super(contactId, actionSetId);
    }

    @Override
    public LegacyActionSetResponse parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        return new LegacyActionSetResponse(rawResponse);
    }
}
