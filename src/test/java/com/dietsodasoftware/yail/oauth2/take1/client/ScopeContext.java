package com.dietsodasoftware.yail.oauth2.take1.client;

import com.dietsodasoftware.yail.oauth2.client.scopes.Scope;

import java.net.URI;
import java.util.Collection;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class ScopeContext {
    private final Collection<Scope> scopes;
    private final String infusionsoftApplicationName;
    private final String authenticationCode;
    private final URI redirectUri;

    public ScopeContext(Collection<Scope> scopes, String infusionsoftApplicationName, String authenticationCode, URI redirectUri) {
        if(scopes == null || scopes.size() == 0){ throw new IllegalArgumentException("must provide scopes"); }
        if(infusionsoftApplicationName == null) { throw new IllegalArgumentException("must provide infusionsoft application name"); }
        if(authenticationCode == null) { throw new IllegalArgumentException("must provide authentication code from the authority"); }
        if(redirectUri == null) { throw new IllegalArgumentException("must provide the same redirect URI given the authority"); }

        this.scopes = scopes;
        this.infusionsoftApplicationName = infusionsoftApplicationName;
        this.authenticationCode = authenticationCode;
        this.redirectUri = redirectUri;
    }

    public Collection<Scope> getScopes(){
        return scopes;
    }

    public String getInfusionsoftApplicationName(){
        return infusionsoftApplicationName;
    }

    String getAuthenticationCode(){
        return authenticationCode;
    }

    URI getRedirectUri(){
        return redirectUri;
    }

    public static ScopeContext fromQueryString(String queryString){
        return null;
    }
}


