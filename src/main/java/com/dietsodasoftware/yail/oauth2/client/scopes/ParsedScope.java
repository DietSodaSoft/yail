package com.dietsodasoftware.yail.oauth2.client.scopes;


import com.dietsodasoftware.yail.oauth2.utils.Lists;

import java.lang.String;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wendel.schultz on 2/4/16.
 */
public class ParsedScope {
    private final List<Scope> scopes;
    private final String appName;

    private ParsedScope(List<Scope> scopes, String appName) {
        this.scopes = scopes;
        this.appName = appName;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public String getAppName() {
        return appName;
    }

    public static ParsedScope fromRawScope(String rawScope) {
        final int barIndex = rawScope.indexOf("|");
        final String appDomain = rawScope.substring(barIndex + 1);
        final String rawScopeNames = rawScope.substring(0,barIndex);

        final String appName = scrubAppDomain(appDomain);
        final String[] scopeNames = rawScopeNames.split(" ");
        final List<Scope> scopes = Lists.newLinkedList();

        for(String name: scopeNames) {
            Scope scope = Scope.fromScopeName(name);
            scopes.add(scope);
        }

        return new ParsedScope(scopes, appName);
    }

    private static String scrubAppDomain(String appDomain) {
        final String appName;
        if(appDomain.endsWith(".infusionsoft.com")){
            appName = appDomain.replace(".infusionsoft.com", "");
        } else {
            appName = appDomain;
        }

        return appName;
    }
}
