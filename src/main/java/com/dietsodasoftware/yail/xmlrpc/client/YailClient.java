package com.dietsodasoftware.yail.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class YailClient {
	static final String APP_LOCATION = "infusionsoft.com";
    private static final String XMLRPC_PATH = "/api/xmlrpc";
	private final String apiKey;

	private final XmlRpcClient infusionApp;

    private YailClient(String appName, String appLocation, String apiKey, String vendorKey){
        this.apiKey = apiKey;

        final String appUrl = "https://" + appName + "." + appLocation + XMLRPC_PATH;
        this.infusionApp = initXmlRpcClient(appUrl);
    }
	
	private YailClient(String appName, String apiKey, String vendorKey){
        this(appName, APP_LOCATION, apiKey, vendorKey);
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
	
	public static YailClient usingApiKey(String appName, String location, String apiKey){
		final YailClient client = new YailClient(appName, location, apiKey, null);
		
		return client;
	}
	
	public String getKey(){
		if(apiKey != null){
			return apiKey;
		}
		throw new IllegalStateException("I have no idea which key to use");
	}

	public <T> T call(InfusionsoftXmlRpcServiceOperation<T> operation) throws InfusionsoftXmlRpcException {
		
		final List<?> parameters = operation.getXmlRpcParameters(this);

        try {
            final Object rawResult = infusionApp.execute(operation.getRpcName(), parameters);
            return operation.parseResult(rawResult);
        } catch (XmlRpcException e) {
            throw new InfusionsoftXmlRpcException("Failure making XmlRpc invocation", e);
        } catch (InfusionsoftResponseParsingException e) {
            throw new InfusionsoftXmlRpcException("Unable to parse XmlRpc response", e);
        }
	}
}
