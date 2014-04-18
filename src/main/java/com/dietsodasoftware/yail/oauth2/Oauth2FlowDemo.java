package com.dietsodasoftware.yail.oauth2;

import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthAuthenticator;
import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthToken;
import com.dietsodasoftware.yail.oauth2.client.OauthAuthenticationException;
import com.dietsodasoftware.yail.oauth2.client.Scope;
import com.dietsodasoftware.yail.oauth2.parse.DietSodaUtils;
import com.dietsodasoftware.yail.oauth2.parse.ParseAuthority;
import com.dietsodasoftware.yail.oauth2.parse.ParseConfiguration;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * User: wendel.schultz
 * Date: 4/11/14
 */
public class Oauth2FlowDemo {

    public static void main(String[] args) throws IOException, OauthAuthenticationException, InterruptedException {

        new Oauth2FlowDemo().authorize();

    }

    // authorize
    // https://signin.infusionsoft.com/app/oauth/authorize
    // http://tools.ietf.org/html/draft-ietf-oauth-v2-21#section-4.1.1
    public void authorize() throws IOException, OauthAuthenticationException, InterruptedException {
        final long timeoutSeconds = 120;
        final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                final Thread thread = new Thread(r);
                thread.setName("Parse Poller");
                thread.setDaemon(true);

                return thread;
            }
        });

        final ParseConfiguration parse = DietSodaUtils.parse();

        final ParseAuthority parseAuthority = ParseAuthority.builder()
                .withClientId(DietSodaUtils.MASHERY_CLIENT_ID)
                .withScopes(Scope.Full)
                .withScheduledExecutorService(executorService)
                .withMaxAttempts(10)
//                .withBasicAuth("bologna", "sandwiches")
                .build(parse);


        final InfusionsoftOauthAuthenticator auth = new InfusionsoftOauthAuthenticator();

        final InfusionsoftOauthToken token = auth.authorize2(parseAuthority, DietSodaUtils.MASHERY_CLIENT_ID, DietSodaUtils.MASHERY_CLIENT_SECRET, timeoutSeconds);

        // use this token as "API Key" in XMLRPC calls AND ?auth_token=<> URL parameter
        System.out.println("Got a auth token: " + token.getToken());

    }

    // token
    // https://api.infusionsoft.com/token
    // http://tools.ietf.org/html/draft-ietf-oauth-v2-21#section-4.1.3
    // do the biz
    // https://api.infusionsoft.com/crm/xmlrpc/v1?access_token=ACCESSTOKEN

}
