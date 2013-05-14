package com.dietsodasoftware.yail.xmlrpc.service.cas;

import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.List;

/**
 * Since MrBean news up the response objects, this little utility makes easy work of sleuthing through them.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/13/13
 * Time: 11:29 PM
 */
public class CASUtils {

    public static List<CASAccount> accountsOfType(CASLogin login, CASAccount.AccountType accountType) {
        final List<CASAccount> accounts = ListFactory.quickLinkedList();

        for(CASAccount account: login.getLinkedApps()) {
            final String typeString = account.getAppType();
            if(typeString == null || "".equalsIgnoreCase(typeString.trim())){ continue; }

            final CASAccount.AccountType type = CASAccount.AccountType.valueOf(typeString);
            if(type != null && type == accountType) {
                accounts.add(account);
            }
        }

        return accounts;
    }

    public static CASAccount crmAppNamed(CASLogin login, String appName) {
        if(appName == null){
            return null;
        }

        final CASAccount.AccountType accountType = CASAccount.AccountType.crm;

        for(CASAccount account: login.getLinkedApps()) {
            final String typeString = account.getAppType();
            if(typeString == null || "".equalsIgnoreCase(typeString.trim())){ continue; }

            final CASAccount.AccountType type = CASAccount.AccountType.valueOf(typeString);
            if(type != null && type == accountType && appName.equalsIgnoreCase(account.getAppName())) {
                return account;
            }
        }

        return null;
    }
}
