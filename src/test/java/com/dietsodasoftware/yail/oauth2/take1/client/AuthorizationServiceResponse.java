package com.dietsodasoftware.yail.oauth2.take1.client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public interface AuthorizationServiceResponse {

    @JsonProperty("state")
    public Object getRequestIdentifier();

    @JsonProperty("scope")
    public String getScope();

    @JsonProperty("code")
    public String getCode();

}
