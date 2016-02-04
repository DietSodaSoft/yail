package com.dietsodasoftware.yail.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftParameterValidationException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateForTemporaryKey;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * Lazily refresh the temporary API key.
 *
 *
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/18/13
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
class VendorKeyAuthenticatingRefreshedApiKeyProvider implements YailClient.ApiKeyProvider {
    private final static long DEFAULT_REFRESH_DELAY_MINUTES = 45;

    private final Object refreshLock = new Object();
    private DateTime lastRefreshTimestamp = null;

    String currentApiKey = null;

    private final String vendorKey;
    private final String username;
    private final String password;

    VendorKeyAuthenticatingRefreshedApiKeyProvider(String vendorKey, String username, String password) {
        this.vendorKey = vendorKey;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getApiKey(YailClient client) throws InfusionsoftXmlRpcException {
        if(shouldRefreshApiKey()){
            synchronized (refreshLock){
                if(shouldRefreshApiKey()){
                    try {
                        currentApiKey = requestFreshApiKey(client);
                    } catch (InfusionsoftParameterValidationException e) {
                        throw new RuntimeException("Invalid client throws an invalid argument exception", e);
                    }
                    lastRefreshTimestamp = DateTime.now();
                }
            }
        }

        return currentApiKey;
    }

    boolean shouldRefreshApiKey(){
        synchronized (refreshLock){
            if(currentApiKey == null || lastRefreshTimestamp == null){
                return true;
            }
            final DateTime now = DateTime.now();
            final Duration maxDuration = Duration.standardSeconds(getRefreshDelaySeconds());
            return lastRefreshTimestamp.plus(maxDuration).isBefore(now);
        }
    }

    // mockability
    long getRefreshDelaySeconds(){
        return Duration.standardMinutes(DEFAULT_REFRESH_DELAY_MINUTES).getStandardSeconds();
    }

    // should be called with the refreshLock obtained.
    private String requestFreshApiKey(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {

        final AuthenticationServiceAuthenticateForTemporaryKey keyAuth = new AuthenticationServiceAuthenticateForTemporaryKey(vendorKey, username, password);
        return client.call(keyAuth);

    }
}
