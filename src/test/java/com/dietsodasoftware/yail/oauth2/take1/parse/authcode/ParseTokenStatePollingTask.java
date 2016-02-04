package com.dietsodasoftware.yail.oauth2.take1.parse.authcode;

import com.dietsodasoftware.yail.oauth2.take1.client.OAuthAuthenticationHandler;
import com.dietsodasoftware.yail.oauth2.take1.client.OauthAuthenticationException;
import com.dietsodasoftware.yail.oauth2.client.scopes.Scope;
import com.dietsodasoftware.yail.oauth2.take1.client.ScopeContext;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class ParseTokenStatePollingTask implements Runnable {
    private final String targetUuid;
    private final TokenCodeStateResource tokenCodeStateResource;
    private final AtomicInteger attemptCount = new AtomicInteger(0);
    private final int maxAttempts;
    private final OAuthAuthenticationHandler authenticationHandler;

    private ScheduledFuture myFuture;

    public ParseTokenStatePollingTask(TokenCodeStateResource tokenCodeStateResource, String targetUuid, int maxAttempts, OAuthAuthenticationHandler authenticationHandler) {
        this.targetUuid = targetUuid;
        this.tokenCodeStateResource = tokenCodeStateResource;
        this.maxAttempts = maxAttempts;
        this.authenticationHandler = authenticationHandler;
    }

    public ParseTokenStatePollingTask setMyFuture(ScheduledFuture future){
        this.myFuture = future;
        return this;
    }

    // get the state
    // curl -i -H "X-Parse-Application-Id: z8NmDvVsa7WPBpqsQGXtrqZRyVlokCNoEb40BdOE" -H "X-Parse-REST-API-Key: m1KOhmEbRcPlCkvGHiPKN71Y2H1zPeXZnACaB9H6" "https://api.parse.com/1/classes/token_states/Teg2cI2Jl9"
    // search for state =
    // curl -i -X GET -H "X-Parse-Application-Id: z8NmDvVsa7WPBpqsQGXtrqZRyVlokCNoEb40BdOE" -H "X-Parse-REST-API-Key: m1KOhmEbRcPlCkvGHiPKN71Y2H1zPeXZnACaB9H6" "https://api.parse.com/1/classes/ds_token_states" --data-urlencode 'where={"state": "23456"}'
    @Override
    public void run() {
        if(attemptCount.getAndIncrement() >= maxAttempts){
            // end of the line
            myFuture.cancel(false);
        } else {
            try {
                pollForAuthorizationToken();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OauthAuthenticationException e) {
                e.printStackTrace();
            }
        }
    }

    private void pollForAuthorizationToken() throws IOException, OauthAuthenticationException {
        final TokenCodeState tokenCodeState = tokenCodeStateResource.readTokenState(targetUuid);

        if(tokenCodeState != null){
            try{
                final ScopeContext scope = fromTokenCodeState(tokenCodeState);

                authenticationHandler.onAuthentication(scope);
                myFuture.cancel(false);
            } finally {
                // if the callback throws, I deem this unauthentiated
                attemptCount.addAndGet(maxAttempts);
            }
        }
    }

    private ScopeContext fromTokenCodeState(TokenCodeState tokenCodeState){
        final String redirectUriString = URLDecoder.decode(tokenCodeState.getRedirectUri());
        final URI redirectUri = URI.create(redirectUriString);

        final String authCode = tokenCodeState.getAuthorizationCode();

        final String rawScope = URLDecoder.decode(tokenCodeState.getScope());

        final int barIndex = rawScope.indexOf("|");
        final String appName = rawScope.substring(barIndex + 1);
        final String rawScopeNames = rawScope.substring(0,barIndex);

        final String[] scopeNames = rawScopeNames.split(" ");
        final List<Scope> scopes = new LinkedList<Scope>();
        for(String scopeName: scopeNames){
            final Scope scope = Scope.fromScopeName(scopeName);
            if(scope != null){
                scopes.add(scope);
            }
        }

        return new ScopeContext(scopes, appName, authCode, redirectUri);
    }
}
