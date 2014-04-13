package com.dietsodasoftware.yail.oauth2.client;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class OauthAuthenticationException extends Exception {
    public OauthAuthenticationException(String message) {
        super(message);
    }

    public OauthAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
