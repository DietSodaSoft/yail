package com.dietsodasoftware.yail.xmlrpc.model.customfields;

import com.dietsodasoftware.yail.xmlrpc.model.CustomField;
import com.dietsodasoftware.yail.xmlrpc.model.CustomField.Field;
import com.dietsodasoftware.yail.xmlrpc.model.CustomField.FormFieldFormatting;
import com.dietsodasoftware.yail.xmlrpc.model.CustomField.Model;

/**
 * User: wendel.schultz
 * Date: 8/29/14
 */
class SimpleNamedCustomField extends SimpleOperationCustomField implements NamedCustomField {
    private final CustomField field;

    SimpleNamedCustomField(CustomField field) {
        super((String) field.getFieldValue(Field.Name));
        this.field = field;
    }

    @Override
    public Model getModel() {
        return field.getEntityModel();
    }

    @Override
    public String getLabel() {
        return field.getFieldValue(Field.Label);
    }

    @Override
    public FormFieldFormatting getFieldFormat() {
        return field.getFieldFormat();
    }

    @Override
    public String getValues(){
        return field.getFieldValue(Field.Values);
    }
}
