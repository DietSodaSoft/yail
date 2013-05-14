package com.dietsodasoftware.yail.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.client.http.InfusionsoftHttpPostRequest;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.service.cas.CASLogin;
import com.dietsodasoftware.yail.xmlrpc.service.cas.InfusionsoftIDLoginRequest;
import com.dietsodasoftware.yail.xmlrpc.service.cas.UserTokens;
import com.dietsodasoftware.yail.xmlrpc.service.paging.AutoForwardPagingIterator;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingBound;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingRequest;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class YailClient {
    interface ApiKeyProvider {
        String getApiKey(YailClient client) throws InfusionsoftXmlRpcException;
    }

    static final String CAS_AUTH_LOCATION = "signin.infusionsoft.com";
	static final String APP_LOCATION = "infusionsoft.com";
    private static final String XMLRPC_PATH = "/api/xmlrpc";
    private final ApiKeyProvider apiKeyProvider;
    private final UserTokens tokens;

	private final XmlRpcClient infusionApp;
    private final String authUrl;

    YailClient(String appName, String appLocation, ApiKeyProvider apiKeyProvider, UserTokens tokens){
        this.apiKeyProvider = apiKeyProvider;
        this.tokens = tokens;

        final String appUrl = "https://" + appName + "." + appLocation + XMLRPC_PATH;
        this.infusionApp = initXmlRpcClient(appUrl);

        authUrl = "https://" + CAS_AUTH_LOCATION;
    }
	
	YailClient(String appName, ApiKeyProvider apiKeyProvider, UserTokens tokens){
        this(appName, APP_LOCATION, apiKeyProvider, tokens);
    }

    private XmlRpcClient initXmlRpcClient(String appUrl){


		final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		
		try {
			config.setServerURL(new URL(appUrl));
		} catch (MalformedURLException e) {
			throw new RuntimeException("You can't recover from this.  Go home.");
		}

		final XmlRpcClient app =  new XmlRpcClient();
		app.setConfig(config);

        return app;
	}
	

	public String getKey() throws InfusionsoftXmlRpcException {
        return apiKeyProvider.getApiKey(this);
	}

	public <T> T call(InfusionsoftXmlRpcServiceOperation<T> operation) throws InfusionsoftXmlRpcException {
		
		final List<?> parameters = operation.getXmlRpcParameters(this);

        try {
            final Object rawResult = infusionApp.execute(operation.getRpcName(), parameters);
            return operation.parseResult(rawResult);
        } catch (XmlRpcException e) {
            throw new InfusionsoftXmlRpcException("Failure making XmlRpc invocation: " + e.getMessage(), e);
        } catch (InfusionsoftResponseParsingException e) {
            throw new InfusionsoftXmlRpcException("Unable to parse XmlRpc response", e);
        }
	}

    public <MT  extends Model, RT extends InfusionsoftModelCollectionOperation<?, MT>> Iterable<MT> autoPage(ForwardPagingRequest<MT, RT> operation){
        return new AutoForwardPagingIterator<MT, RT>(this, operation);
    }

    public <MT  extends Model, RT extends InfusionsoftModelCollectionOperation<?, MT>> Iterable<MT> autoPage(ForwardPagingRequest<MT, RT> operation, ForwardPagingBound<MT> stopper){
        return new AutoForwardPagingIterator<MT, RT>(this, operation, stopper);
    }

    /**
     * Will throw IllegalArgumentException if the client is API key-based.  This can only work if the client has the
     * username and password.
     *
     * @return
     * @throws IOException
     */
    public CASLogin authenticateWithInfusionsoftID() throws IOException {
        return authenticateWithInfusionsoftID(tokens);
    }

    /**
     * Use the Profile using vendor key, username and passowrd.  This is here until the API key is deprectated completely.
     */
    @Deprecated
    public CASLogin authenticateWithInfusionsoftID(final String username, final String password) throws IOException {
        final UserTokens tokens = new UserTokens() {
            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return password;
            }
        };

        return authenticateWithInfusionsoftID(tokens);
    }

    // OK fine: this isn't deprecated per se.  This will really become private or subsumed in the no-arg variant above.
    @Deprecated
    public CASLogin authenticateWithInfusionsoftID(final UserTokens tokens) throws IOException {
        final InfusionsoftIDLoginRequest login = new InfusionsoftIDLoginRequest(tokens);
        return post(login);
    }

    /**
     * This is needed for the CAS signin.  There are other Infusionsoft goodies that make use of POST requests, so this
     * can live here with "reasonable cause."  "Reasonable" because that's the way it is done, not because it is a good
     * way to design/model this stuff.
     *
     */
    public <T> T post(InfusionsoftHttpPostRequest<T> isftRequest) throws IOException {
        isftRequest.validateArguments();

        final HttpEntity body = isftRequest.getPostBody();
        final DefaultHttpClient client = new DefaultHttpClient();
        final String path = isftRequest.getRequestPath();
        final String uriString = authUrl +"/"+ path;
        final URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(uriString);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI: " + uriString, e);
        }

        // unnecessary ATM.  May switch to using JSON, so let's not get ahead of ourselves here.
        for(NameValuePair pair: isftRequest.getUrlParameters()){
            uriBuilder.addParameter(pair.getName(), pair.getValue());
        }

        final Request httpRequest;
        try {
            httpRequest = Request.Post(uriBuilder.build()).body(body);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI: " + uriString, e);
        }

        final Response httpResponse = httpRequest.execute();
        return isftRequest.parseResponse(httpResponse);
    }



}
