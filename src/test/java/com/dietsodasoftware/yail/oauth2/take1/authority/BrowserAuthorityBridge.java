package com.dietsodasoftware.yail.oauth2.take1.authority;

import com.dietsodasoftware.yail.oauth2.take1.client.OAuthAuthenticationHandler;
import com.dietsodasoftware.yail.oauth2.take1.client.Scope;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.http.client.utils.URIBuilder;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class BrowserAuthorityBridge {

    private static final String DEFAULT_OAUTH_ENDPOINT = "https://signin.infusionsoft.com";
    private static final String AUTHORIZATION_PATH = "/app/oauth/authorize";

//    private static final String SUCCESSFUL_AUTHENTICATION_REDIRECT_LOCATION = "http://www.dietsodasoftware.com/infusionsoft/oauth2.php";
    private static final String SUCCESSFUL_AUTHENTICATION_REDIRECT_LOCATION = "https://dietsoda.parseapp.com/infusionsoft-oauth2";

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
    private final String authorizationUri;

    private String username;
    private String password;

    private OAuthAuthenticationHandler authenticationHandler;

    public BrowserAuthorityBridge(String authorizationRedirectUri, String clientId, Scope... scopes){
        if(scopes == null){ throw new IllegalArgumentException("Must provide scopes to authorize against"); }
        if(clientId == null){ throw new IllegalArgumentException("Must provide client ID"); }
        if(authorizationRedirectUri == null) { throw new IllegalArgumentException("Must provide redirect URI"); }

        this.clientId = clientId;
        this.scopeList = Arrays.asList(scopes);

        uuid = UUID.randomUUID().toString();
        authorizationUri =  authorizationRedirectUri;
    }

    public BrowserAuthorityBridge(String clientId, Scope... scopes){
        this(SUCCESSFUL_AUTHENTICATION_REDIRECT_LOCATION, clientId, scopes);
    }

    public BrowserAuthorityBridge usingBasicAuth(String username, String password){
        this.username = username;
        this.password = password;

        return this;
    }

    /**
     *  Kick off a platform-native default browser.
     *
     *  Non-blocking.
     *
     * @throws IOException if the native platform can't show the browser.
     */
    public void showBrowser() throws IOException {
        final URI redirectUri = createAuthorityUri(authorizationUri, uuid);
        final Desktop desktop = Desktop.getDesktop();

        desktop.browse(redirectUri);
    }

    public String getRequestUuid(){
        return uuid;
    }



    private URI createAuthorityUri(String successfulRedirectUri, String stateParameter){

        final URI redirectURI;
        try {
            final URIBuilder builder = new URIBuilder(successfulRedirectUri);
            if(username != null && password != null){
                builder.setUserInfo(username, password);
            }
            redirectURI = builder.build();
        } catch (URISyntaxException x) {
            throw new IllegalArgumentException(x.getMessage(), x);
        }

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
