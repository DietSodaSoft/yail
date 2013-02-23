package com.dietsodasoftware.isft.xmlrpc.client;

public class IsftProfile {
	
	private String vendorKey;
	private String apiKey;
	
	private final String appName;
	
	private IsftProfile(String appName){
		this.appName = appName;
	}
	
	public static IsftProfile usingVendorKey(String appName, String vendorKey){
		final IsftProfile profile = new IsftProfile(appName);
		profile.vendorKey = vendorKey;
		
		return profile;
	}
	
	public static IsftProfile usingApiKey(String appName, String apiKey){
		final IsftProfile profile = new IsftProfile(appName);
		profile.apiKey = apiKey;
		
		return profile;
	}
	
	public IsftClient getClient(){
		final IsftClient client;
		if(vendorKey != null){
			client = IsftClient.usingVendorKey(appName, vendorKey);
		} else if(apiKey != null) {
			client = IsftClient.usingApiKey(appName, apiKey);
		} else {
			throw new IllegalArgumentException("I have no idea");
		}
		
		return client;		
	}

}
