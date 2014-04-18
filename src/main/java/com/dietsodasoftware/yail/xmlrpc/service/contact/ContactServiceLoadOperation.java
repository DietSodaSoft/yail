package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 11:16 PM
 */
@InfusionsoftRpc(service = "ContactService", method = "load")
public class ContactServiceLoadOperation extends InfusionsoftModelOperation<Contact, Contact> {

    private final Integer contactId;
    private final List<String> returnFields;
    public ContactServiceLoadOperation(Integer contactId){
        super(Contact.class);

        this.contactId = contactId;
        returnFields = ListFactory.quickLinkedList();
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> params = ListFactory.quickLinkedList();
        params.add(contactId);
        if(returnFields.isEmpty()){
            params.add(getAllModelReturnFieldNames());
        } else {
            params.add(returnFields);
        }

        return params;
    }

    @Override
    public Contact parseResult(Object rawResponse) {
        if(rawResponse == null){
            return null;
        }
        return createModelInstance((Map<String,Object>) rawResponse);
    }


    protected ContactServiceLoadOperation addReturnFieldName(String fieldName){
        returnFields.add(fieldName);
        return this;
    }

    public ContactServiceLoadOperation addReturnFieldName(NamedField field){
        return addReturnFieldName(field.name());
    }

    public ContactServiceLoadOperation addCustomReturnFieldName(String customFieldName){
        return addReturnFieldName("_" + scrubCustomFieldName(customFieldName));
    }


}
