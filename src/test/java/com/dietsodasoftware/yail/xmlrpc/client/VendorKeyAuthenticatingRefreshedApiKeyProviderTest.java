package com.dietsodasoftware.yail.xmlrpc.client;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateForTemporaryKey;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/18/13
 * Time: 1:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorKeyAuthenticatingRefreshedApiKeyProviderTest {

    @Mock
    private YailClient client;

    @InjectMocks
    private VendorKeyAuthenticatingRefreshedApiKeyProvider provider = new VendorKeyAuthenticatingRefreshedApiKeyProvider("v", "u", "p");

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);

        provider = spy(provider);
    }

    @Test
    public void testUninitializedProviderRequestsApiKey() throws InfusionsoftXmlRpcException {

        assertTrue(provider.shouldRefreshApiKey());

        provider.getApiKey(client);

        verify(client).call(any(AuthenticationServiceAuthenticateForTemporaryKey.class));
    }

    @Test
    public void testMaxDurationNotElapsedDoesNotRefresh() throws InfusionsoftXmlRpcException {
        final String firstExpectedKey = "first";
        final String secondExpectedKey = "second";

        when(provider.getRefreshDelaySeconds()).thenReturn(15L);
        assertTrue(provider.shouldRefreshApiKey());

        when(client.call(any(AuthenticationServiceAuthenticateForTemporaryKey.class))).thenReturn(firstExpectedKey, secondExpectedKey);

        final String firstKey = provider.getApiKey(client); // should initialize current key and last timestamp
        assertEquals(firstExpectedKey, firstKey);

        verify(client).call(any(AuthenticationServiceAuthenticateForTemporaryKey.class));

        assertFalse(provider.shouldRefreshApiKey());

        sleepSeconds(3L);
        assertFalse(provider.shouldRefreshApiKey());

        sleepSeconds(3L);
        assertFalse(provider.shouldRefreshApiKey());

        final String stillFirstKey = provider.getApiKey(client);
        assertEquals(stillFirstKey, firstExpectedKey);

        verify(client, times(1)).call(any(AuthenticationServiceAuthenticateForTemporaryKey.class));

    }

    @Test
    public void testMaxDurationExceededRefreshes() throws InfusionsoftXmlRpcException {
        final String firstExpectedKey = "first";
        final String secondExpectedKey = "second";

        when(provider.getRefreshDelaySeconds()).thenReturn(5L);
        assertTrue(provider.shouldRefreshApiKey());

        when(client.call(any(AuthenticationServiceAuthenticateForTemporaryKey.class))).thenReturn(firstExpectedKey, secondExpectedKey);

        final String firstKey = provider.getApiKey(client); // should initialize current key and last timestamp
        assertEquals(firstExpectedKey, firstKey);

        verify(client).call(any(AuthenticationServiceAuthenticateForTemporaryKey.class));

        assertFalse(provider.shouldRefreshApiKey());

        sleepSeconds(6L);
        assertTrue(provider.shouldRefreshApiKey());

        final String secondKey = provider.getApiKey(client);
        assertEquals(secondExpectedKey, secondKey);

        verify(client, times(2)).call(any(AuthenticationServiceAuthenticateForTemporaryKey.class));

    }

    @Test
    public void testDefaultDelayValueIs45Minutes(){
        final long fortyFiveMinutesInSeconds = Duration.standardMinutes(45L).getStandardSeconds();
        assertEquals(fortyFiveMinutesInSeconds, provider.getRefreshDelaySeconds());
    }

    private void sleepSeconds(Long duration){
        try {
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }
}
