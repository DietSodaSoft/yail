package com.dietsodasoftware.yail.xmlrpc.client;

/**
 * This Builder prefers API key.  So if all are set, it will prefer the API key credentials in creating a {@link YailProfile}.
 *
 *
 * User: wendel.schultz
 * Date: 9/5/14
 */
public class YailProfileBuilder {

    private final String appName;


    private String apiKey;

    private String vendorKey;
    private String username;
    private String password;


    private String location;

    public YailProfileBuilder(String appName){
        this.appName = appName;
    }

    public YailProfileBuilder usingApiKey(String apiKey){
        this.apiKey = apiKey;
        return this;
    }

    public YailProfileBuilder atLocation(String location){
        this.location = location;
        return this;
    }


    public YailProfileBuilder usingVendorKey(String vendorKey, String username, String password){
        this.vendorKey = vendorKey;
        this.username = username;
        this.password = password;
        return this;
    }


    public YailProfile build(){
        if(apiKey == null && vendorKey == null){
            throw new IllegalArgumentException("Must provide either apiKey or vendorKey");
        }

        final YailProfile profile;
        if (apiKey != null) {
            // api key authentication
            if(location == null){
                profile = YailProfile.usingApiKey(appName, apiKey);
            } else {
                profile = YailProfile.atLocationUsingApiKey(appName, location, apiKey);
            }
        } else {
            // vendor key authenticaiton
            if(vendorKey == null || username == null || password == null){
                throw new IllegalArgumentException("If using vendorKey, username and password are required");
            }
            if(location == null){
                profile = YailProfile.usingVendorKey(appName, vendorKey, username, password);
            } else {
                profile = YailProfile.atLocationUsingVendorKey(appName, location, vendorKey, username, password);
            }
        }

        return profile;
    }
}
