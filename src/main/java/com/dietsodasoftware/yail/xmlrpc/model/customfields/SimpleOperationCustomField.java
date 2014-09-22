package com.dietsodasoftware.yail.xmlrpc.model.customfields;

import com.dietsodasoftware.yail.xmlrpc.model.Model;

/**
 * User: wendel.schultz
 * Date: 9/2/14
 */
public class SimpleOperationCustomField implements OperationCustomField {
    private final String columnName;

    /**
     * This is the column name, NOT including the magic "_"
     */
    public SimpleOperationCustomField(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getApiArgument() {
        return "_" + getColumnName();
    }

    @Override
    public String getColumnName(){
        return columnName;
    }

    public static SimpleOperationCustomField forCustomFieldNamed(String customFieldName){
        return new SimpleOperationCustomField(Model.scrubCustomFieldName(customFieldName));
    }
}
