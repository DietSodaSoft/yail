package com.dietsodasoftware.yail.xmlrpc.service.email;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.*;


@InfusionsoftRpc(service = "APIEmailService", method = "sendEmailWithStatus")
public class EmailServiceSendEmailWithStatusOperation extends InfusionsoftXmlRpcServiceOperation<List<SendEmailWithStatusResult>> {

    private final SendEmailWithStatusArguments arguments;

    public EmailServiceSendEmailWithStatusOperation(SendEmailWithStatusArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
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
    public List<SendEmailWithStatusResult> parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse != null){
            Object[] rawResponses = (Object[])rawResponse;
            List<SendEmailWithStatusResult> results = new ArrayList<SendEmailWithStatusResult>(rawResponses.length);
            for(Object rawResp : rawResponses ) {
                Map<String,String> struct = (Map<String,String>)rawResp;
                results.add(new SendEmailWithStatusResult(struct));
            }
            return results;
        } else {
            return Collections.emptyList();
    }
    }
}
