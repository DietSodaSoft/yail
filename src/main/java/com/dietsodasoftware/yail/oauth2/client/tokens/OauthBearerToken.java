package com.dietsodasoftware.yail.oauth2.client.tokens;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wendel.schultz on 2/3/16.
 *
 *  the repsponse looks like:

   {
        "access_token": "ewu9et2bv2j2jdysduptm5ta",
        "expires_in": 28800,
        "refresh_token": "dgrdnwcxbpkvkbdqew3hu525",
        "scope": "full|zt119.infusionsoft.com",
        "token_type": "bearer"
   }

 */
public interface OauthBearerToken {

    @JsonProperty("access_token")
    public String getAccessToken();

    @JsonProperty("expires_in")
    public Long getExpiresIn();

    @JsonProperty("refresh_token")
    public String getRefreshToken();

    @JsonProperty("scope")
    public String getRawScope();

    @JsonProperty("token_type")
    public String getTokenType();

}
