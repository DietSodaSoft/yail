package com.dietsodasoftware.yail.xmlrpc.service.cas;

import java.util.List;

/**
 * YAIL relies on Jackson/MrBean to hydrate these return types, so an interface which maps to the data contained in the
 * responses is all that is necessary.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/13/13
 * Time: 8:16 PM
 */
public interface CASLogin {

    public String getCasGlobalId();

    public String getUsername();

    public String getDisplayName();

    public String getFirstName();

    public String getLastName();

    public List<CASAccount> getLinkedApps();

    public List<String> getAuthorities();

    public String getCode();

    public String getMessage();
}
