package com.dietsodasoftware.yail.xmlrpc.service.email;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@InfusionsoftRpc(service = "APIEmailService", method = "sendTemplateWithStatus")
public class EmailServiceSendTemplateWithStatusOperation extends InfusionsoftXmlRpcServiceOperation<List<SendTemplateWithStatusResult>> {

    private final SendTemplateWithStatusArguments arguments;

    public EmailServiceSendTemplateWithStatusOperation(SendTemplateWithStatusArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> args = new LinkedList<Object>();
        return ListFactory.quickUnmodifiableLinkedList(
                arguments.contactList,
                arguments.templateId
        );
    }


    @Override
    public List<SendTemplateWithStatusResult> parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse != null){
            List<SendTemplateWithStatusResult> results = new ArrayList<SendTemplateWithStatusResult>();
            Object[] rawResponses = (Object[])rawResponse;
            for(Object rawResp : rawResponses ) {
                Map<String,String> struct = (Map<String,String>)rawResp;
                results.add(new SendTemplateWithStatusResult(struct));
            }
            return results;
        } else {
            return null;
        }
    }
}
