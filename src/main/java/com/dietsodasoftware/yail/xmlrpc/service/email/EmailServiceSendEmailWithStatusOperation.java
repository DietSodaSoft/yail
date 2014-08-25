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


@InfusionsoftRpc(service = "APIEmailService", method = "sendEmailWithStatus")
public class EmailServiceSendEmailWithStatusOperation extends InfusionsoftXmlRpcServiceOperation<List<SendEmailWithStatusResult>> {

    private final SendEmailWithStatusArguments arguments;

    public EmailServiceSendEmailWithStatusOperation(SendEmailWithStatusArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> args = new LinkedList<Object>();
        return ListFactory.quickUnmodifiableLinkedList(
                arguments.fromAddress,
                arguments.toAddresses,
                arguments.ccAddresses,
                arguments.bccAddresses,
                arguments.subject,
                arguments.textBody,
                arguments.htmlBody
        );
    }


    @Override
    public List<SendEmailWithStatusResult> parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {if(rawResponse != null){
        List<SendEmailWithStatusResult> results = new ArrayList<SendEmailWithStatusResult>();
        Object[] rawResponses = (Object[])rawResponse;
        for(Object rawResp : rawResponses ) {
            Map<String,String> struct = (Map<String,String>)rawResp;
            results.add(new SendEmailWithStatusResult(struct));
        }
        return results;
    } else {
        return null;
    }
    }
}
