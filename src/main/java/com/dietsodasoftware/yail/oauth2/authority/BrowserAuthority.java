package com.dietsodasoftware.yail.oauth2.authority;

import com.dietsodasoftware.yail.oauth2.client.OAuthAuthenticationHandler;
import com.dietsodasoftware.yail.oauth2.client.OauthAuthenticationAuthority;
import com.dietsodasoftware.yail.oauth2.client.OauthAuthenticationException;
import com.dietsodasoftware.yail.oauth2.client.Scope;
import com.dietsodasoftware.yail.oauth2.client.ScopeContext;
import com.dietsodasoftware.yail.oauth2.parse.authcode.ParseTokenStatePollingTask;
import edu.emory.mathcs.backport.java.util.*;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class BrowserAuthority implements OauthAuthenticationAuthority {

    private static final String DEFAULT_OAUTH_ENDPOINT = "https://signin.infusionsoft.com";
    private static final String AUTHORIZATION_PATH = "/app/oauth/authorize";

    private static final String SUCCESSFUL_AUTHENTICATION_REDIRECT_LOCATION = "http://www.dietsodasoftware.com/infusionsoft/oauth2.php";

    private static final String URL_ENCODING = "UTF8";
    private static final String AUTH_PARAMETER_CLIENT_ID = "client_id";
    private static final String AUTH_PARAMETER_REDIRECT_URI = "redirect_uri";
    private static final String AUTH_PARAMETER_RESPONSE_TYPE = "response_type";
    private static final String AUTH_PARAMETER_RESPONSE_TYPE_CODE_VALUE = "code";
    private static final String AUTH_PARAMETER_SCOPE = "scope";
    private static final String AUTH_PARAMETER_STATE = "state";

    private final List<Scope> scopeList;
    private final String clientId;

    private final String uuid;
    private final URI authorizationUri;

    private OAuthAuthenticationHandler authenticationHandler;

    public BrowserAuthority(String clientId, Scope... scopes){
        if(scopes == null){ throw new IllegalArgumentException("Must provide scopes to authorize against"); }
        if(clientId == null){ throw new IllegalArgumentException("Must provide client ID"); }

        this.clientId = clientId;
        this.scopeList = Arrays.asList(scopes);

        uuid = UUID.randomUUID().toString();
        authorizationUri = createAuthorityUri(uuid);
    }


    /**
     *  Kick off a native default browser.
     *
     * @param handler ignored.
     * @throws IOException
     * @throws OauthAuthenticationException
     */
    @Override
    public void attemptAuthorization(OAuthAuthenticationHandler handler) throws IOException, OauthAuthenticationException {
        final Desktop desktop = Desktop.getDesktop();

        desktop.browse(authorizationUri);
    }

    public String getRequestUuid(){
        return uuid;
    }



    private URI createAuthorityUri(String stateParameter){

        final URI redirectURI = URI.create(SUCCESSFUL_AUTHENTICATION_REDIRECT_LOCATION);

        final String scopesParam;
        if(scopeList.contains(Scope.Full)){
            scopesParam = Scope.Full.getScopeName();
        } else {
            final StringBuilder scopeString = new StringBuilder();
            for(int i = 0; i < scopeList.size(); i++){
                scopeString.append(scopeList.get(i).getScopeName());
                if( i < scopeList.size() - 1){
                    scopeString.append(" ");
                }
            }

            scopesParam = scopeString.toString();
        }

        final String authorizeUrlString = DEFAULT_OAUTH_ENDPOINT + AUTHORIZATION_PATH;
        final URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(authorizeUrlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Bad authorization URL from SDK provider.  YAIL for the loss.", e);
        }

        uriBuilder.addParameter(AUTH_PARAMETER_CLIENT_ID, clientId);
        uriBuilder.addParameter(AUTH_PARAMETER_RESPONSE_TYPE, AUTH_PARAMETER_RESPONSE_TYPE_CODE_VALUE);
        uriBuilder.addParameter(AUTH_PARAMETER_SCOPE, scopesParam);
        uriBuilder.addParameter(AUTH_PARAMETER_REDIRECT_URI, redirectURI.toString());
        uriBuilder.addParameter(AUTH_PARAMETER_STATE, stateParameter);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Bad authorization URL from SDK provider.  YAIL for the loss.", e);
        }
    }
}
