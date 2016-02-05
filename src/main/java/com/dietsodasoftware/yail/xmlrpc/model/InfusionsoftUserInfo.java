package com.dietsodasoftware.yail.xmlrpc.model;

import java.util.Map;

/**
 * User: wendel.schultz
 * Date: 4/18/14
 */
public class InfusionsoftUserInfo {
    private final Map<String, Object> modelMap;

    private final static String KEY_GLOBAL_USER_ID = "globalUserId";
    private final static String KEY_LOCAL_USER_ID ="localUserId";
    private final static String KEY_APP_ALIAS = "appAlias";
    private final static String KEY_APP_URL = "appUrl";
    private final static String KEY_DISPLAY_NAME = "displayName";
    private final static String KEY_CAS_USERNAME = "casUsername";


    public InfusionsoftUserInfo(final Map<String, Object> modelMap) {
        this.modelMap = modelMap;
    }

    // guaranteed to not be null
    public Integer getGlobalUserId(){
        return (Integer) modelMap.get(KEY_GLOBAL_USER_ID);
    }

    // guaranteed to not be null
    public Integer getLocalUserId(){
        return (Integer) modelMap.get(KEY_LOCAL_USER_ID);
    }

    public String getAppAlias(){
        return (String) modelMap.get(KEY_APP_ALIAS);
    }

    public String getAppUrl(){
        return (String) modelMap.get(KEY_APP_URL);
    }

    public String getDisplayName(){
        return (String) modelMap.get(KEY_DISPLAY_NAME);
    }

    public String getUsername(){
        return (String) modelMap.get(KEY_CAS_USERNAME);
    }


    @Override
    public String toString() {
        return new org.apache.commons.lang.builder.ToStringBuilder(this)
                .append("modelMap", modelMap)
                .toString();
    }
}
