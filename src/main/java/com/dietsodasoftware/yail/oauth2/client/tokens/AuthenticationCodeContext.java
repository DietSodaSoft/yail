package com.dietsodasoftware.yail.oauth2.client.tokens;

import com.dietsodasoftware.yail.oauth2.client.scopes.ParsedScope;

import java.util.Objects;

/**
 * Created by wendel.schultz on 2/2/16.
 */
public class AuthenticationCodeContext {
    private final String state;
    private final String scope;
    private final String appName;
    private final String code;
    private final String redirectUrl;

    public AuthenticationCodeContext(String scope, String state, String code, String redirectUrl) {
        Objects.requireNonNull(scope, "must provide scope");
        Objects.requireNonNull(state, "must provide state");
        Objects.requireNonNull(code, "must provide code");
        Objects.requireNonNull(redirectUrl, "must provide redirectUrl");
        this.state = state;
        this.code = code;
        this.redirectUrl = redirectUrl;

        ParsedScope parsed = ParsedScope.fromRawScope(scope);

        this.scope = parsed.getScopes().get(0).getScopeName();
        this.appName = parsed.getAppName();
    }

    public String getState() {
        return state;
    }

    public String getScope() {
        return scope;
    }

    public String getAppName() {
        return appName;
    }

    public String getCode() {
        return code;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("state=").append(state)
                .append("scope=").append(scope)
                .append("appName=").append(appName)
                .append("code=").append(code)
                .append("redirectUrl=").append(redirectUrl)
                .toString();
    }
}
