package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Handles contact creation
 */
public class ContactServiceAddOperation extends InfusionsoftXmlRpcServiceOperation<Integer> {
    private static final String RPC_NAME = "ContactService.add";

    private final Contact contact;

    public ContactServiceAddOperation(Contact contact) {
        this.contact = contact;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> params = new LinkedList<Object>();

        params.add(contact.getStruct());

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public Integer parseResult(Object rawResponse) throws InfusionsoftResponseParsingException {
        return (Integer) rawResponse;
    }
}
