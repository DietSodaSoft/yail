package com.dietsodasoftware.yail.xmlrpc.service.email;

/**
 * User: wendel.schultz
 * Date: 1/13/14
 */
class MergeFieldTemplate {
    static String getMergeFieldTemplate(MergeFieldName name){
        return "~" + name.getMergeFieldName() + "~";
    }
}
