package com.dietsodasoftware.yail.oauth2.take1.client;

import java.io.IOException;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public interface OAuthAuthenticationHandler {

    void onAuthentication(ScopeContext scopeContext) throws IOException;

}
