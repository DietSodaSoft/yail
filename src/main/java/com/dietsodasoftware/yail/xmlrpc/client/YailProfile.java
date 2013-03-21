package com.dietsodasoftware.yail.xmlrpc.client;

public class YailProfile {
	
	private String apiKey;

	private final String appName;
    private final String location;

    private YailProfile(String appName, String location){
		this.appName = appName;
        this.location = location;
	}
	
	public static YailProfile usingApiKey(String appName, String apiKey){
        return atLocationUsingApiKey(appName, YailClient.APP_LOCATION, apiKey);
	}

    public static YailProfile atLocationUsingApiKey(String appName, String location, String apiKey){
        final YailProfile profile = new YailProfile(appName, location);
        profile.apiKey = apiKey;

        return profile;
    }

    public YailClient getClient(){
		return YailClient.usingApiKey(appName, location, apiKey);
	}

}
