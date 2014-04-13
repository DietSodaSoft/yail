package com.dietsodasoftware.yail.oauth2.parse.authcode;

import com.dietsodasoftware.yail.oauth2.parse.ParseConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class TokenCodeStateResource {
    private final ParseConfiguration parseConfiguration;

    private static final String PARSE_ENDPOINT = "https://api.parse.com";
    private static final String TOKEN_STATE_PATH = "/1/classes/ds_token_states";

    public TokenCodeStateResource(ParseConfiguration parseConfiguration) {
        this.parseConfiguration = parseConfiguration;
    }

    // curl -i -X GET -H "X-Parse-Application-Id: z8NmDvVsa7WPBpqsQGXtrqZRyVlokCNoEb40BdOE" -H "X-Parse-REST-API-Key: m1KOhmEbRcPlCkvGHiPKN71Y2H1zPeXZnACaB9H6"  "https://api.parse.com/1/classes/token_states?where={"state": "23456"} "
    TokenCodeState readTokenState(String requestUuid) throws IOException {
        final URI read = tokenStatesUri(requestUuid);
        final Request request = parseConfiguration.configureRequest(Request.Get(read));
        final TokenCodeState tokenCode = request.execute().handleResponse(new ResponseHandler<TokenCodeState>() {

            @Override
            public TokenCodeState handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                final TokenCodeState tokenCode;
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    TokenCodeSearchResults results = parseConfiguration.parseJsonResponse(response.getEntity().getContent(), TokenCodeSearchResults.class);

                    if(results == null || results.results == null || results.results.size() == 0){
                        tokenCode = null;
                    } else {
                        tokenCode = results.results.get(0);
                    }
                } else {
                    tokenCode = null;
                }

                return tokenCode;
            }
        });

        return tokenCode;
    }

    // write the state
    // curl -i -X POST -H "X-Parse-Application-Id: z8NmDvVsa7WPBpqsQGXtrqZRyVlokCNoEb40BdOE" -H "X-Parse-REST-API-Key: m1KOhmEbRcPlCkvGHiPKN71Y2H1zPeXZnACaB9H6"   -H "Content-Type: application/json"   -d '{"auth_code": 45, "state": "3161036c-05ea-47e9-abd5-f54fe5eb88be" }' "https://api.parse.com/1/classes/token_states"
//    TokenCodeState saveNewTokenCode(final String uniqueRequestId, final String authorizationCode) throws IOException {
//        final TokenCodeState newState = new TokenCodeState() {
//            @Override
//            public String getAuthorizationCode() {
//                return authorizationCode;
//            }
//
//            @Override
//            public String getRequestUuid() {
//                return uniqueRequestId;
//            }
//
//            @Override
//            public String getObjectId() {
//                return null; // don't know this yet
//            }
//
//            @Override
//            public DateTime getUpdatedDate() {
//                return null;  // none yet
//            }
//
//            @Override
//            public DateTime getCreationDate() {
//                return null;  // none yet
//            }
//        };
//        final URI save = tokenStatesUri();
//        final Request request = parseConfiguration.configureRequest(Request.Post(save), newState);
//
//        final Response response = request.execute();
//        if(response.returnResponse().getStatusLine().getStatusCode() == HttpStatus.SC_CREATED){
//            final String jsonContent = response.returnContent().asString();
//            final TokenCodeState partial = parseConfiguration.parseJsonResponse(jsonContent, TokenCodeState.class);
//
//            return readTokenState(partial.getObjectId());
//        }
//
//        return null;
//    }

    private static final URI tokenStatesUri(final String requestUuid){
        if(requestUuid == null) {throw new IllegalArgumentException("request state must be provided"); }
        final String uriString = PARSE_ENDPOINT + TOKEN_STATE_PATH;
        try {
            return new URIBuilder(uriString)
                    .addParameter("where", String.format("{\"state\": \"%s\"}", requestUuid))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Bad Parse URL from SDK provider.  YAIL for the loss.", e);
        }
    }

    private static final URI tokenStatesUri(){
        final String uriString = PARSE_ENDPOINT + TOKEN_STATE_PATH;
        try {
            return new URIBuilder(uriString).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Bad Parse URL from SDK provider.  YAIL for the loss.", e);
        }
    }

    private static class TokenCodeSearchResults{

        @JsonProperty(required = false)
        private List<TokenCodeState> results;
    }
}
