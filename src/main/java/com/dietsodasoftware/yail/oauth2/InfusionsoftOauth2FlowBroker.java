package com.dietsodasoftware.yail.oauth2;

import com.dietsodasoftware.yail.oauth2.client.scopes.Scope;
import com.dietsodasoftware.yail.oauth2.client.tokens.AuthenticationCodeContext;
import com.dietsodasoftware.yail.oauth2.client.tokens.CodeStateGenerator;
import com.dietsodasoftware.yail.oauth2.client.tokens.OauthBearerToken;
import com.dietsodasoftware.yail.oauth2.httpclient.fluent.OauthContentResponseHandler;
import com.dietsodasoftware.yail.oauth2.utils.Lists;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Created by wendel.schultz on 2/2/16.
 */
public class InfusionsoftOauth2FlowBroker {

    public static final String AUTH_PARAMETER_RESPONSE_TYPE_CODE = "code";
    public static final String TOKEN_PARAMETER_CODE_GRANT_TYPE_VALUE = "authorization_code";
    public static final String TOKEN_PARAMETER_REFRESH_GRANT_TYPE_VALUE = "refresh_token";


    public static final String INFUSIONSOFT_SIGNIN_URI = "https://signin.infusionsoft.com/app/oauth/authorize";
    public static final String INFUSIONSOFT_TOKEN_GRANTING_URI = "https://api.infusionsoft.com/token";


    private enum OauthRedirectParameters {
        ClientId("client_id"),
        RedirectUri("redirect_uri"),
        ResponseType("response_type"),
        Scope("scope"),
        State("state")
        ;
        private final String parameter;

        OauthRedirectParameters(String parameter) {
            this.parameter = parameter;
        }

        URIBuilder decorate(URIBuilder uri, String value) {
            return uri.addParameter(parameter, value);
        }
    }

    private enum OauthTokenGrantingParameters {
        GrantType("grant_type"),
        Code("code"),
        ClientId("client_id"),
        ClientSecret("client_secret"),
        RedirectUri("redirect_uri")
        ;
        private final String parameter;

        OauthTokenGrantingParameters(String parameter) {
            this.parameter = parameter;
        }

        URIBuilder decorate(URIBuilder uri, String value) {
            return uri.addParameter(parameter, value);
        }

        NameValuePair pair(String value) {
            return new BasicNameValuePair(parameter, value);
        }
    }

    private enum OauthTokenRefreshParameters {
        GrantType("grant_type"),
        RefreshToken("refresh_token"),
        RedirectUri("redirect_uri")
        ;

        private final String parameter;

        OauthTokenRefreshParameters(String parameter) {
            this.parameter = parameter;
        }

        NameValuePair pair(String value) {
            return new BasicNameValuePair(parameter, value);
        }
    }


    private final String clientId;
    private final String secret;
    // 'Basic ' + base64_encode(CLIENT_ID + ':' + CLIENT_SECRET)
    private final String refreshAuthHeaderValue;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new MrBeanModule());


    private String infusionsoftOauthSigninPage = INFUSIONSOFT_SIGNIN_URI;
    private String infusionsoftTokenGrantingUrl = INFUSIONSOFT_TOKEN_GRANTING_URI;
    private CodeStateGenerator stateGenerator;


    private InfusionsoftOauth2FlowBroker(String clientId, String secret) {
        this.clientId = clientId;
        this.secret = secret;
        this.refreshAuthHeaderValue = createBasicAuthHeaderValue(clientId, secret);
    }


    private static String createBasicAuthHeaderValue(String clientId, String secret) {

        final String encoded;
        try {
            encoded = Base64.getEncoder().encodeToString((clientId +":" + secret).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Java no longer understands its own utf-8 character encoding.");
        }

        return "Basic " + encoded;
    }

    public URI createOauthSigninRedirectUri(String oauthCallbackUri) {
        final String state = stateGenerator.createRequestState();

        URIBuilder uri = new URIBuilder(URI.create(infusionsoftOauthSigninPage));

        uri = OauthRedirectParameters.ClientId.decorate(uri, clientId);
        uri = OauthRedirectParameters.ResponseType.decorate(uri, AUTH_PARAMETER_RESPONSE_TYPE_CODE);
        uri = OauthRedirectParameters.Scope.decorate(uri, Scope.Full.getScopeName());

        // the link service and HATEOAS crap only works in the context of a MVC call.
        uri = OauthRedirectParameters.RedirectUri.decorate(uri, oauthCallbackUri);
        uri = OauthRedirectParameters.State.decorate(uri, state);

        try {
            return uri.build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("The world has changed how to make a URI");
        }
    }

    public OauthBearerToken createGrantTicketFromAuthCode(AuthenticationCodeContext codeContext) throws IOException {

        final List<NameValuePair> tokenParameters = Lists.newArrayList(
                OauthTokenGrantingParameters.ClientId.pair(clientId),
                OauthTokenGrantingParameters.ClientSecret.pair(secret),
                OauthTokenGrantingParameters.Code.pair(codeContext.getCode()),

                OauthTokenGrantingParameters.GrantType.pair(TOKEN_PARAMETER_CODE_GRANT_TYPE_VALUE),
                OauthTokenGrantingParameters.RedirectUri.pair(codeContext.getRedirectUrl())
        );

        final Request request = Request.Post(infusionsoftTokenGrantingUrl)
                .bodyForm(tokenParameters)
            ;

        final Response response;
        try {
            response = request.execute();
            final Content content = response.handleResponse(new OauthContentResponseHandler());
            final String responseContent = content.asString();

            return mapper.readValue(responseContent, OauthBearerToken.class);
        } catch (JsonParseException e) {
            throw new RuntimeException("Infusionsoft's protocol seemingly has changed.  Invalid Token service response.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Infusionsoft's protocol seemingly has changed.  Invalid Token service response.", e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("HTTP Protocol Error.", e);
        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * AN example exchange:

     curl -i -X POST -H "Authorization: Basic bndoZzQyanJ6cjk0OTh6ZHhrdXA5d3c5OnFkVU1HN2NGY00=" https://api.infusionsoft.com/token -d 'grant_type=refresh_token&refresh_token=ndacp26m8vhtm3g3bqu2qhvy'
     HTTP/1.1 200 OK
     Cache-Control: no-store
     Content-Type: application/json;charset=UTF-8
     Date: Thu, 04 Feb 2016 16:31:03 GMT
     Pragma: no-cache
     Server: Mashery Proxy
     X-Mashery-Responder: prod-j-worker-us-west-1c-65.mashery.com
     transfer-encoding: chunked
     Connection: keep-alive

     {"access_token":"36aa7gax3exenvdrt8222kyn","token_type":"bearer","expires_in":28800,"refresh_token":"fxfhvvw4r4wsg764zntn7xyr","scope":"full|zt119.infusionsoft.com"}

     */
    public OauthBearerToken refreshBearerToken(String refreshToken) throws IOException {

        final List<NameValuePair> tokenParameters = Lists.newArrayList(
                OauthTokenRefreshParameters.GrantType.pair(TOKEN_PARAMETER_REFRESH_GRANT_TYPE_VALUE),
                OauthTokenRefreshParameters.RefreshToken.pair(refreshToken)
        );

        final Request request = Request.Post(infusionsoftTokenGrantingUrl)
                .addHeader("Authorization", refreshAuthHeaderValue)
                .bodyForm(tokenParameters)
                ;

        final Response response;
        try {
            response = request.execute();
            final Content content = response.handleResponse(new OauthContentResponseHandler());
            final String responseContent = content.asString();

            return mapper.readValue(responseContent, OauthBearerToken.class);
        } catch (JsonParseException e) {
            throw new RuntimeException("Infusionsoft's protocol seemingly has changed.  Invalid Token service response.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Infusionsoft's protocol seemingly has changed.  Invalid Token service response.", e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("HTTP Protocol Error.", e);
        } catch (IOException e) {
            throw e;
        }

    }

    public static Builder builder(String clientId, String secret) {
        return new Builder(clientId, secret);
    }

    public static class Builder {
        private final String clientId;
        private final String secret;

        private String infusionsoftOauthSigninPage = INFUSIONSOFT_SIGNIN_URI;
        private String infusionsoftTokenGrantingUrl = INFUSIONSOFT_TOKEN_GRANTING_URI;
        private CodeStateGenerator stateGenerator = new CodeStateGenerator() {
            @Override
            public String createRequestState() {
                return UUID.randomUUID().toString();
            }
        };


        public Builder(String clientId, String secret) {
            this.clientId = clientId;
            this.secret = secret;
        }

        public Builder withCodeStateGenerator(CodeStateGenerator stateGenerator) {
            this.stateGenerator = stateGenerator;
            return this;
        }

        public Builder withInfusionsoftOauthSigninPageUri(String signinPageUri) {
            this.infusionsoftOauthSigninPage = signinPageUri;
            return this;
        }

        public Builder withInfusionsoftTokenGrantingUri(String tokenGrantingUri) {
            this.infusionsoftTokenGrantingUrl = tokenGrantingUri;
            return this;
        }


        public InfusionsoftOauth2FlowBroker build() {
            if (clientId == null) throw new IllegalArgumentException("must provide clientId");
            if (secret == null) throw new IllegalArgumentException("must provide secret");
            if (stateGenerator == null) throw new IllegalArgumentException("stateGenerator must not be null");
            if (infusionsoftOauthSigninPage == null) throw new IllegalArgumentException("infusionsoftOauthSigninPage must not be null");
            if (infusionsoftTokenGrantingUrl == null) throw new IllegalArgumentException("infusionsoftTokenGrantingUrl must not be null");

            final InfusionsoftOauth2FlowBroker flow = new InfusionsoftOauth2FlowBroker(clientId, secret);
            flow.stateGenerator = stateGenerator;
            flow.infusionsoftOauthSigninPage = infusionsoftOauthSigninPage;
            flow.infusionsoftTokenGrantingUrl = infusionsoftTokenGrantingUrl;

            return flow;
        }
    }
}
