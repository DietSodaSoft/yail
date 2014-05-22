package com.dietsodasoftware.yailext.task.customfields;

import com.dietsodasoftware.yail.xmlrpc.model.CustomField;
import com.dietsodasoftware.yail.xmlrpc.model.CustomFieldHeader;
import com.dietsodasoftware.yail.xmlrpc.model.CustomFieldTab;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/21/13
 * Time: 8:29 PM
 */
public class CustomFieldDescription {
    private final CustomField field;
    private final CustomFieldHeader header;
    private final CustomFieldTab tab;

    public CustomFieldDescription(CustomField field, CustomFieldHeader header, CustomFieldTab tab) {
        this.field = field;
        this.header = header;
        this.tab = tab;
    }

    public CustomFieldTab getCustomFieldTab(){
        return tab;
    }

    public CustomFieldHeader getCustomFieldHeader(){
        return header;
    }

    public CustomField getCustomField(){
        return field;
    }

    @Override
    public String toString() {
        return "CustomFieldDescription{" +
                "field=" + field +
                ", header=" + header +
                ", tab=" + tab +
                '}';
    }
}
