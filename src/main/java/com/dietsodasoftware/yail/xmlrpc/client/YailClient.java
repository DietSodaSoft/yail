package com.dietsodasoftware.yail.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionOperation;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftParameterValidationException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.service.paging.AutoForwardPagingIterator;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingBound;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingRequest;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
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
    private final YailUserAgent userAgent;

	private final XmlRpcClient infusionApp;
    private final String authUrl;

    YailClient(String appName, String appLocation, ApiKeyProvider apiKeyProvider){
        this.userAgent = new YailUserAgent();
        this.apiKeyProvider = apiKeyProvider;

        final String appUrl = "https://" + appName + "." + appLocation + XMLRPC_PATH;
        this.infusionApp = initXmlRpcClient(appUrl);

        authUrl = "https://" + CAS_AUTH_LOCATION;
    }
	
	YailClient(String appName, ApiKeyProvider apiKeyProvider){
        this(appName, APP_LOCATION, apiKeyProvider);
    }

    private XmlRpcClient initXmlRpcClient(String appUrl){


		final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setUserAgent(userAgent.getUserAgentString());

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

	public <T> T call(InfusionsoftXmlRpcServiceOperation<T> operation) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {

		final List<?> parameters = operation.getXmlRpcParameters(this);

        operation.validateArguments();

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


}
