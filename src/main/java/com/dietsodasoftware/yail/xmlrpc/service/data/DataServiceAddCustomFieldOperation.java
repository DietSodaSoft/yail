package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.CustomField;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Add a custom field to a type: Contact, Order, etc (CustomField.Model) under a header by headerId;
 */
@InfusionsoftRpc(service = "DataService", method = "addCustomField")
public class DataServiceAddCustomFieldOperation extends SimpleRpcServiceOperation<Integer> {//} InfusionsoftXmlRpcServiceOperation<Integer> {

    public DataServiceAddCustomFieldOperation(CustomField.Model model, CustomField.FormFieldFormatting format, Integer headerId, String label){
        super(model.getModelTypeDbName(), label, format.getDbTypeName(), headerId);
    }

}
