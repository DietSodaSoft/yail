package com.dietsodasoftware.yail.oauth2.take1.client;

import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthToken;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
class DelegatingInfusionsoftOauthToken implements InfusionsoftOauthToken{
    InfusionsoftOauthToken delegate = new NoOpInfusionsoftOauthToken();

    public String getToken() {
        return delegate.getToken();
    }


    private static class NoOpInfusionsoftOauthToken implements InfusionsoftOauthToken{

        @Override
        public String getToken() {
            return null;
        }
    }
}
