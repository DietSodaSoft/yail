package com.dietsodasoftware.yail.xmlrpc.client;


import org.junit.Test;

/**
 * User: wendel.schultz
 * Date: 12/19/13
 */
public class YailUserAgentTest {

    @Test
    public void testUserAgentString(){
        final YailUserAgent userAgent = new YailUserAgent();
        System.err.println("User agent: " + userAgent.getUserAgentString());
    }

}
