package com.dietsodasoftware.yail.oauth2.parse;

import com.dietsodasoftware.yail.oauth2.authority.BrowserAuthority;
import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthToken;
import com.dietsodasoftware.yail.oauth2.client.OAuthAuthenticationHandler;
import com.dietsodasoftware.yail.oauth2.client.OauthAuthenticationAuthority;
import com.dietsodasoftware.yail.oauth2.client.OauthAuthenticationException;
import com.dietsodasoftware.yail.oauth2.client.Scope;
import com.dietsodasoftware.yail.oauth2.parse.authcode.ParseTokenResourceService;
import com.dietsodasoftware.yail.oauth2.parse.authcode.ParseTokenStatePollingTask;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class ParseAuthority implements OauthAuthenticationAuthority {

    private final ParseConfiguration parse;
    private ScheduledExecutorService executorService;

    private String clientId;
    private Scope[] scopes;

    private int maxAttempts = 5;
    private long initialDelaySeconds = 5;
    private long delaySeconds = 3;



    private ParseAuthority(ParseConfiguration parse) {
        if(parse == null) { throw new IllegalArgumentException("must provide a parse configuration"); }
        this.parse = parse;
    }

    @Override
    public void attemptAuthorization(OAuthAuthenticationHandler handler) throws IOException, OauthAuthenticationException {
        final BrowserAuthority browser = new BrowserAuthority(clientId, scopes);
        browser.attemptAuthorization(null);

        final String requestUuid = browser.getRequestUuid();
        final ParseTokenResourceService tokenService = new ParseTokenResourceService(parse);
        final ParseTokenStatePollingTask pollingTask = tokenService.createTokenPollingTask(requestUuid, maxAttempts, handler);

        final ScheduledFuture future = executorService.scheduleWithFixedDelay(pollingTask, initialDelaySeconds, delaySeconds, TimeUnit.SECONDS);
        pollingTask.setMyFuture(future);


    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String clientId;
        private Scope[] scopes;
        private ScheduledExecutorService executorService;

        private Long initialDelaySeconds;
        private Long delaySeconds;
        private Integer maxAttempts;

        private Builder(){}

        public Builder withClientId(String clientId){
            this.clientId = clientId;
            return this;
        }

        public Builder withScopes(Scope... scopes){
            this.scopes = scopes;
            return this;
        }

        public Builder withScheduledExecutorService(ScheduledExecutorService executorService){
            this.executorService = executorService;
            return this;
        }

        public Builder withInitialDelaySeconds(long delay){
            initialDelaySeconds = delay;
            return this;
        }

        public Builder withDelaySeconds(long delay){
            delaySeconds = delay;
            return this;
        }

        public Builder withMaxAttempts(int attempts){
            this.maxAttempts = attempts;
            return this;
        }


        public ParseAuthority build(ParseConfiguration configuration){
            final ParseAuthority auth = new ParseAuthority(configuration);
            auth.clientId = clientId;
            auth.scopes = scopes;

            if(executorService == null){
                executorService = new ScheduledThreadPoolExecutor(1);
            }
            auth.executorService = executorService;

            if(initialDelaySeconds != null){
                auth.initialDelaySeconds = initialDelaySeconds;
            }

            if(delaySeconds != null){
                auth.delaySeconds = delaySeconds;
            }

            if(maxAttempts != null){
                auth.maxAttempts = maxAttempts;
            }

            return auth;
        }
    }
}
