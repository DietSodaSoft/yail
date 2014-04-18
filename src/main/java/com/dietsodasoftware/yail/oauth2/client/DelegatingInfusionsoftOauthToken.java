package com.dietsodasoftware.yail.oauth2.client;

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
