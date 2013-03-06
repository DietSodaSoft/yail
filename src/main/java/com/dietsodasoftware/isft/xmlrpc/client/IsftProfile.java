package com.dietsodasoftware.isft.xmlrpc.client;

public class IsftProfile {
	
	private String apiKey;

	private final String appName;
    private final String location;

    private IsftProfile(String appName, String location){
		this.appName = appName;
        this.location = location;
	}
	
	public static IsftProfile usingApiKey(String appName, String apiKey){
        return atLocationUsingApiKey(appName, IsftClient.APP_LOCATION, apiKey);
	}

    public static IsftProfile atLocationUsingApiKey(String appName, String location, String apiKey){
        final IsftProfile profile = new IsftProfile(appName, location);
        profile.apiKey = apiKey;

        return profile;
    }

    public IsftClient getClient(){
		return IsftClient.usingApiKey(appName, location, apiKey);
	}

}
