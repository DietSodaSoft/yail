package com.dietsodasoftware.yail.xmlrpc.service.email;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.ArgumentValidator;
import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * User: kldavis4
 * Date: 6/13/14
 */
@InfusionsoftRpc(service = "APIEmailService", method = "optIn")
public class EmailServiceOptInOperation extends SimpleRpcServiceOperation<Boolean> {

    private String email;
    private String reason;

    @Override
    protected List<Object> getOperationParameters() {
        final List<Object> parameters = new LinkedList<Object>();

        final String reasonString;

        if(reason != null){
            reasonString = reason;
        } else {
            reasonString = "API Opt In";
        }

        parameters.add(email);
        parameters.add(reasonString);

        return parameters;
    }

    @ArgumentValidator
    public void validate(){
        if(email == null) throw new IllegalArgumentException("Must provide email: address");
    }


    public EmailServiceOptInOperation setEmail(String email) {
        this.email = email;
        return this;
    }

    public EmailServiceOptInOperation setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
