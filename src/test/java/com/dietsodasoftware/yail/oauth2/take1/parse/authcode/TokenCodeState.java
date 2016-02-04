package com.dietsodasoftware.yail.oauth2.take1.parse.authcode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
@JsonInclude(Include.NON_NULL)
public interface TokenCodeState {

    @JsonProperty("authorizationCode")
    public String getAuthorizationCode();

    // this maps the Infusionsoft API client request back to the end user's permission grant
    @JsonProperty("state")
    public String getRequestUuid();

    @JsonProperty("objectId")
    public String getObjectId();

    @JsonProperty("scope") // i think this can go
    public String getScope();

    @JsonProperty("redirectUri")
    public String getRedirectUri();


    @JsonProperty("updatedAt")
    public DateTime getUpdatedDate();

    @JsonProperty("createdAt")
    public DateTime getCreationDate();

}
