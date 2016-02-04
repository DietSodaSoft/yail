package com.dietsodasoftware.yail.oauth2.client.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Some errors look like:
 *
 *  {"error":"invalid_request","error_description":"Missing redirect_uri”}
 *
 *  {"error":"invalid_grant","error_description":"Authorization code is invalid”}
 *
 */
public interface OauthProtocolError {

    @JsonProperty(value = "error", required = false)
    public String getError();

    @JsonProperty(value = "error_description", required = false)
    public String getErrorDescription();

}
