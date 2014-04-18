package com.dietsodasoftware.yail.oauth2.client;

import java.io.IOException;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public interface OauthAuthenticationAuthority {

    public void attemptAuthorization(OAuthAuthenticationHandler handler) throws IOException, OauthAuthenticationException;

}
