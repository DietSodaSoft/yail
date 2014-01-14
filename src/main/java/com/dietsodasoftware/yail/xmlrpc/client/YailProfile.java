package com.dietsodasoftware.yail.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateForTemporaryKey;
import com.dietsodasoftware.yail.xmlrpc.utils.DigestionUtils;

/**
 * A YAIL Profile is a DTO for the application access information: app name/location, and the relevant tokens necessary
 * to access the Infusionsoft app at that location.  it is also a factory for creating clients which utilize the tokens
 * while accessing the Infusionsoft XMLRPC API.  All the details of how things work aren't terribly relevant to using
 * YAIL, since it negotiates it for you, but it is outlined briefly here for the curious.
 *
 * There are two ways to obtain access an Infusionsoft app: using the app's API key as found in the admin section of the app
 * itself, {@linkplain https://<appname></appname>.infusionsoft.com/app/miscSetting/itemWrapper?systemId=nav.admin&settingModuleName=Application&settingTabName=Application}
 * or if you are an integration, you access it using a Vendor key and a user's username/password.
 *
 * The {@link YailProfile} acts as a factory for a {@link YailClient}.  The client dispatches requests to the Infusionsoft
 * app, and since the first argument of every API call is the API key, the client provides that argument, rather than
 * each operation individually somehow finding a way to inject it.  See {@link YailClient#call(InfusionsoftXmlRpcServiceOperation)}
 * for more.  The only operation which doesn't take an API key as its first argument is the vendor key authorization
 * operation, {@link AuthenticationServiceAuthenticateForTemporaryKey}, but the base operation class
 * {@link InfusionsoftXmlRpcServiceOperation} assembles arguments for you and you don't need to do anything explicitly
 * for it.
 *
 * If you are fortunate enough to have the app's API key, then you are golden; nothing more is needed because it is the
 * "Golden Ticket."  However, if you use Vendor Keys, client code must request a temporary API access key and refresh it because
 * after an hour it is no longer valid.  The {@link YailClient} is newed up with an {@link YailClient.ApiKeyProvider},
 * which removes the dirty secrets of how an API key comes into existence.
 *
 * There are two {@link YailClient.ApiKeyProvider}s: {@link FixedApiKeyProvider} and {@link VendorKeyAuthenticatingRefreshedApiKeyProvider}.
 * It should be pretty obvious what they do and why.  The FixedApiKeyProvider hangs onto the API key and returns it at
 * each invocation of {@link YailClient.ApiKeyProvider#getApiKey(YailClient)}, ignoring the client.
 * VendorKeyAuthenticatingRefreshedApiKeyProvider requests a new API key upon invocation if the API key it is currently holding
 * is more than 45 minutes old.  It does this inline, blocking.  This means that if you are doing a DataService query
 * and it has been more than 45 minutes since the last API key was obtained, that your query operation will block waiting
 * for the client to make an additional web service call to refresh the API key, at which point the query operation will
 * use the new API key for it's web service invocation.
 *
 * The Infusionsoft API takes MD5-hashed passwords.  YAIL expects a plain text password, and at the last possible minute
 * digests the password: See
 * {@link AuthenticationServiceAuthenticateForTemporaryKey#AuthenticationServiceAuthenticateForTemporaryKey(String, String, String)}
 *
 */
public class YailProfile {
	
	private final YailClient.ApiKeyProvider apiKeyProvider;

	private final String appName;
    private final String location;

    YailProfile(String appName, String location, YailClient.ApiKeyProvider apiKeyProvider){
        this.apiKeyProvider = apiKeyProvider;
		this.appName = appName;
        this.location = location;
	}
	
	public static YailProfile usingApiKey(String appName, String apiKey){
        return atLocationUsingApiKey(appName, YailClient.APP_LOCATION, apiKey);
	}

    public static YailProfile atLocationUsingApiKey(String appName, String location, String apiKey){
        final YailClient.ApiKeyProvider keys = new FixedApiKeyProvider(apiKey);
        final YailProfile profile = new YailProfile(appName, location, keys);

        return profile;
    }

    public static YailProfile usingVendorKey(String appName, String vendorKey, String username, String password){
        return atLocationUsingVendorKey(appName, YailClient.APP_LOCATION, vendorKey, username, password);
    }

    public static YailProfile atLocationUsingVendorKey(String appName, String location, String vendorKey, final String username, final String password){
        final String hashword = DigestionUtils.getMD5ForString(password);
        final YailClient.ApiKeyProvider keys = new VendorKeyAuthenticatingRefreshedApiKeyProvider(vendorKey, username, password);
        return new YailProfile(appName, location, keys);
    }

    public YailClient getClient(){
		return new YailClient(appName, location, apiKeyProvider);
	}

}
