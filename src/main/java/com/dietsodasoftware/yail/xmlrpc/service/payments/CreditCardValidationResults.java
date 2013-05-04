package com.dietsodasoftware.yail.xmlrpc.service.payments;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 11:17 AM
 */
public class CreditCardValidationResults {
    private final Map<String, String> struct;
    CreditCardValidationResults(Map<String, String> struct){
        this.struct = struct;
    }

    public boolean isValid(){
        final String valid = struct.get("Valid");
        if("True".equalsIgnoreCase(valid)){
            return true;
        }
        return false;
    }

    public String getMessage(){
        return struct.get("Message");
    }
}
