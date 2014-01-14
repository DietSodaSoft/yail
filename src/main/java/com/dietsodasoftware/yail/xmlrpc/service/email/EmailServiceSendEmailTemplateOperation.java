package com.dietsodasoftware.yail.xmlrpc.service.email;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.ArgumentValidator;
import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * User: wendel.schultz
 * Date: 1/13/14
 */
@InfusionsoftRpc(service = "APIEmailService", method = "sendTemplate")
public class EmailServiceSendEmailTemplateOperation extends SimpleRpcServiceOperation<Boolean> {
    private final Long templateId;
    private List<Long> contactIds = new LinkedList<Long>();

    public EmailServiceSendEmailTemplateOperation(Long templateId) {
        this.templateId = templateId;
    }

    public EmailServiceSendEmailTemplateOperation addContact(Contact contact){
        if(contact != null && contact.getFieldValue(Contact.Field.Id) != null){
            final Long id = contact.getFieldValue(Contact.Field.Id);
            contactIds.add(id);
        }

        return this;
    }

    @Override
    protected List<Object> getOperationParameters() {
        final List<Object> parameters = new LinkedList<Object>();

        final String contactIdsString = StringUtils.join(contactIds, ",");
        parameters.add(contactIdsString);

        return parameters;
    }

    @ArgumentValidator
    public void validate(){
        if(contactIds.size() == 0) throw new IllegalArgumentException("Must provide at least one contact");
    }
}
