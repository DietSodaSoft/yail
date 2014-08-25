package com.dietsodasoftware.yail.xmlrpc.service.contact;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:28 PM
 */
public class LegacyActionSetResult {
    private final boolean isError;
    private final String action;
    private final String message;

    public LegacyActionSetResult(Map<String, Object> struct){
        this.isError = (Boolean) struct.get("IsError");
        this.action = (String) struct.get("Action");
        this.message = (String) struct.get("Message");
    }

    public boolean isError(){
        return isError;
    }

    public String getAction(){
        return action;
    }

    public String getMessage(){
        return message;
    }
}
