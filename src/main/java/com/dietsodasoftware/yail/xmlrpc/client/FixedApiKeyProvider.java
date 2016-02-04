package com.dietsodasoftware.yail.xmlrpc.client;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/18/13
 * Time: 1:00 AM
 * To change this template use File | Settings | File Templates.
 */
class FixedApiKeyProvider implements YailClient.ApiKeyProvider {
    private final String apiKey;

    FixedApiKeyProvider(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getApiKey(YailClient client) {
        return apiKey;
    }
}
