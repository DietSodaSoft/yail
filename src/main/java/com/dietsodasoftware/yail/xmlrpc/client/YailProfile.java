package com.dietsodasoftware.yail.xmlrpc.client;

public class YailProfile {
	
	private YailClient.ApiKeyProvider apiKeyProvider;

	private final String appName;
    private final String location;

    private YailProfile(String appName, String location, YailClient.ApiKeyProvider apiKeyProvider){
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

    public static YailProfile atLocationUsingVendorKey(String appName, String location, String vendorKey, String username, String password){
        final YailClient.ApiKeyProvider keys = new VendorKeyAuthenticatingRefreshedApiKeyProvider(vendorKey, username, password);
        return new YailProfile(appName, location, keys);
    }

    public YailClient getClient(){
		return new YailClient(appName, location, apiKeyProvider);
	}

}
