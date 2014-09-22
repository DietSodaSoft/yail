package com.dietsodasoftware.yail.xmlrpc.model.customfields;

/**
 * User: wendel.schultz
 * Date: 9/2/14
 */
public interface OperationCustomField {

    // Implementations take care to prefix the field name with the "_"
    public String getApiArgument();

    // This is the name of the column without the "_"
    public String getColumnName();

}
