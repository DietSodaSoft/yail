package com.dietsodasoftware.yail.oauth2.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Once an authority has authorized via end-user credentials, we then ask a token service for a token based on the auth
 * code.  This is the response from the token server.
 *
 * User: wendel.schultz
 * Date: 4/12/14
 */
public interface TokenServiceResponse {

    @JsonProperty("token_type")
    String getTokenType();

    @JsonProperty("mapi")
    String getMapi();

    @JsonProperty("access_token")
    String getAccessToken();

    @JsonProperty("scope")
    String getRawScope();

}
