package com.dietsodasoftware.isft.xmlrpc.client;

import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class IsftClient {
	private static final String APP_URL = ".infusionsoft.com/api/xmlrpc";
	private final String apiKey;
	private final String vendorKey;
	
	private final XmlRpcClient infusionApp;
	
	private IsftClient(String appName, String apiKey, String vendorKey){
		this.apiKey = apiKey;
		this.vendorKey = vendorKey;
		
		final String appUrl = "https://" + appName + APP_URL;
		
		final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		
		try {
			config.setServerURL(new URL(appUrl));
		} catch (MalformedURLException e) {
			throw new RuntimeException("You can't recover from this.  Go home.");
		}

		infusionApp =  new XmlRpcClient();
		infusionApp.setConfig(config);
	}
	
	public static IsftClient usingVendorKey(String appName, String vendorKey){
		final IsftClient client = new IsftClient(appName, null, vendorKey);
		
		return client;
	}
	
	public static IsftClient usingApiKey(String appName, String apiKey){
		final IsftClient client = new IsftClient(appName, apiKey, null);
		
		return client;
	}
	
	public String getKey(){
		if(apiKey != null){
			return apiKey;
		}
		
		if(vendorKey != null){
			return vendorKey;
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
