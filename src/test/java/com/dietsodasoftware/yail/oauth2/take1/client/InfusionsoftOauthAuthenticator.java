package com.dietsodasoftware.yail.oauth2.take1.client;

import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthToken;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class InfusionsoftOauthAuthenticator {
    private static final String DEFAULT_TOKEN_ENDPOINT = "https://api.infusionsoft.com";
    private static final String TOKEN_PATH = "/token";

    // curl -i -X POST "https://api.infusionsoft.com/token" -d 'response_type=code&code=2ftcvryh4hgypm79cv2dw7hs&client_id=nwhg42jrzr9498zdxkup9ww9&client_secret=qdUMG7cFcM&redirect_uri=http://dietsodasoftware.com/infusionsoft/oath/SUCCESS'
    // curl -i -X POST "https://api.infusionsoft.com/token"
    //     -d 'response_type=code
    //         &code=2ftcvryh4hgypm79cv2dw7hs
    //         &client_id=nwhg42jrzr9498zdxkup9ww9
    //         &client_secret=qdUMG7cFcM
    //         &redirect_uri=http://dietsodasoftware.com/infusionsoft/oath/SUCCESS'
    private static final String TOKEN_PARAMETER_GRANT_TYPE = "grant_type";
    private static final String TOKEN_PARAMETER_GRANT_TYPE_VALUE = "authorization_code";
    private static final String TOKEN_PARAMETER_CODE = "code";
    private static final String TOKEN_PARAMETER_CLIENT_ID = "client_id";
    private static final String TOKEN_PARAMETER_CLIENT_SECRET = "client_secret";
    private static final String TOKEN_PARAMETER_REDIRECT_URI = "redirect_uri";

    private final ObjectMapper mapper;

    public InfusionsoftOauthAuthenticator(){
        mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.registerModule(new MrBeanModule());
    }

    /**

    [authorizationQueryString appendString:@" response_type=code"];
    [authorizationQueryString appendFormat:@"&client_id=%@", ISLAuthenticationViewControllerClientId];
    [authorizationQueryString appendFormat:@"&client_secret=%@", ISLAuthenticationViewControllerClientSecret];
    [authorizationQueryString appendFormat:@"&redirect_uri=%@", ISLAuthenticationViewControllerCallbackURL];


    [tokenPOSTBodyString appendString:@"grant_type=authorization_code"];
    [tokenPOSTBodyString appendFormat:@"&code=%@", authorizationCode];
    [tokenPOSTBodyString appendFormat:@"&client_id=%@", ISLAuthenticationViewControllerClientId];
    [tokenPOSTBodyString appendFormat:@"&client_secret=%@", ISLAuthenticationViewControllerClientSecret];
    [tokenPOSTBodyString appendFormat:@"&redirect_uri=%@", ISLAuthenticationViewControllerCallbackURL];

     */

    public InfusionsoftOauthToken authorize2(final OauthAuthenticationAuthority authority, final String clientId, final String clientSecret, long timeoutSeconds) throws IOException, OauthAuthenticationException, InterruptedException {


        final DelegatingInfusionsoftOauthToken delegate = new DelegatingInfusionsoftOauthToken();
        final Object waitLock = new Object();

        final OAuthAuthenticationHandler handler = new OAuthAuthenticationHandler() {
            @Override
            public void onAuthentication(ScopeContext scopeContext) throws IOException {
                if(scopeContext == null){ throw new IllegalArgumentException("Cannot authenticate without a scope context"); }

                final String tokenUriString = DEFAULT_TOKEN_ENDPOINT + TOKEN_PATH;
                final URIBuilder tokenUri;
                try {
                    tokenUri = new URIBuilder(tokenUriString);
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Bad authorization URL from SDK provider.  YAIL for the loss.", e);
                }

                tokenUri.addParameter(TOKEN_PARAMETER_CLIENT_ID, clientId);
                tokenUri.addParameter(TOKEN_PARAMETER_CLIENT_SECRET, clientSecret);
                tokenUri.addParameter(TOKEN_PARAMETER_CODE, scopeContext.getAuthenticationCode());
                tokenUri.addParameter(TOKEN_PARAMETER_GRANT_TYPE, TOKEN_PARAMETER_GRANT_TYPE_VALUE);
                tokenUri.addParameter(TOKEN_PARAMETER_REDIRECT_URI, scopeContext.getRedirectUri().toString());

                final Request request;

                try {
                    request = Request.Post(tokenUriString)
                        .bodyString(tokenUri.build().getQuery(), ContentType.APPLICATION_FORM_URLENCODED)
                        ;
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Bad authorization URL for provided client and callback.", e);
                }

                final TokenServiceResponse token;
                final Response response;
                try {
                    response = request.execute();
                    final String responseContent = response.returnContent().asString();

                    token = mapper.readValue(responseContent, TokenServiceResponse.class);
                } catch (JsonParseException e) {
                    throw new RuntimeException("Infusionsoft's protocol seemingly has changed.  Invalid Token service response.", e);
                } catch (JsonMappingException e) {
                    throw new RuntimeException("Infusionsoft's protocol seemingly has changed.  Invalid Token service response.", e);
                } catch (ClientProtocolException e) {
                    throw new RuntimeException("HTTP Protocol Error.", e);
                } catch (IOException e) {
                    throw e;
                }


                delegate.delegate = new InfusionsoftOauthToken() {
                    @Override
                    public String getToken() {
                        return token.getAccessToken();
                    }
                };

                synchronized (waitLock){
                    waitLock.notify();
                }

            }
        };

        authority.attemptAuthorization(handler);
        synchronized (waitLock){
            waitLock.wait(timeoutSeconds * 1000);
        }

        return delegate;
    }

    public InfusionsoftOauthToken authorize(final String clientId, Scope... scopes) throws IOException {


//        final Request request;
//
//        try {
//            request = Request.Get(uriBuilder.build());
//        } catch (URISyntaxException e) {
//            throw new RuntimeException("Bad authorization URL for provided client and callback.", e);
//        }
//        final Response response = request.execute();
//
//        final int statusCode = response.returnResponse().getStatusLine().getStatusCode();
//        if(statusCode == HttpStatus.SC_MOVED_TEMPORARILY){
//            // YAY!!! We won!
//            final Header[] headers = response.returnResponse().getHeaders("Location");
//            if(headers == null || headers.length != 1){
//                throw new IllegalStateException("Infusionsoft's protocol seems to have changed.  Expected response header 'Location' isn't right. ");
//            }
//
//            final Header locationHeader = headers[0];
//            final String location = locationHeader.getValue();
//
//            final URIBuilder callbackUri;
//            try {
//                callbackUri = new URIBuilder(location);
//            } catch (URISyntaxException e) {
//                throw new IllegalStateException("Infusionsoft's protocol seems to be corrupt.  Invalid callback URI.", e);
//            }
//
//            final List<NameValuePair> callbackParams = callbackUri.getQueryParams();
//
//        }

        return null;
    }

    // example authorize URL
    // https://signin.infusionsoft.com/app/oauth/authorize?client_id=8eeupjmupbfqgvuwvbumwr7c&redirect_uri=https%3A%2F%2Fdeveloper.infusionsoft.com%2Fio-docs%2Foauth2callback&response_type=code&scope=read+write+execute
    // https://signin.infusionsoft.com/app/oauth/authorize?
    // client_id=8eeupjmupbfqgvuwvbumwr7c
    // &redirect_uri=https%3A%2F%2Fdeveloper.infusionsoft.com%2Fio-docs%2Foauth2callback
    // &response_type=code
    // &scope=read+write+execute

}
