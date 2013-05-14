package com.dietsodasoftware.yail.xmlrpc.service.cas;

/**
 * YAIL relies on Jackson/MrBean to hydrate these return types, so an interface which maps to the data contained in the
 * responses is all that is necessary.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/13/13
 * Time: 8:16 PM
 */
public interface CASAccount {

    public enum AccountType {
        crm, customerhub, community;
    }

    public String getAppType();

    public String getAppUsername();

    public String getAppName();

    public String getAppAlias();

    public String getAppUrl();
}
