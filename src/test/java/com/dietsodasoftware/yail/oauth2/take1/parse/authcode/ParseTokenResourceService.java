package com.dietsodasoftware.yail.oauth2.take1.parse.authcode;

import com.dietsodasoftware.yail.oauth2.take1.client.OAuthAuthenticationHandler;
import com.dietsodasoftware.yail.oauth2.take1.parse.ParseConfiguration;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class ParseTokenResourceService {
    private final ParseConfiguration parseConfiguration;

    public ParseTokenResourceService(ParseConfiguration parseConfiguration) {
        this.parseConfiguration = parseConfiguration;
    }

    public TokenCodeStateResource createTokenStateResource(){
        return new TokenCodeStateResource(parseConfiguration);
    }

    public ParseTokenStatePollingTask createTokenPollingTask(String requestUuid, int maxAttempts, OAuthAuthenticationHandler authenticationHandler){
        final TokenCodeStateResource codeResource = createTokenStateResource();
        return new ParseTokenStatePollingTask(codeResource, requestUuid, maxAttempts, authenticationHandler);
    }
}
