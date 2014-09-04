package com.dietsodasoftware.yail.xmlrpc.model.customfields;

import com.dietsodasoftware.yail.xmlrpc.model.CustomField.FormFieldFormatting;
import com.dietsodasoftware.yail.xmlrpc.model.CustomField.Model;

/**
 * User: wendel.schultz
 * Date: 8/29/14
 */
public interface NamedCustomField extends OperationCustomField {

    public Model getModel();

    public String getLabel();

    public FormFieldFormatting getFieldFormat();

    public String getValues();
}
