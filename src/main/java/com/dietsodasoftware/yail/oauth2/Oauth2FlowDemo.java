package com.dietsodasoftware.yail.oauth2;

import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthAuthenticator;
import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthToken;
import com.dietsodasoftware.yail.oauth2.client.OauthAuthenticationException;
import com.dietsodasoftware.yail.oauth2.client.Scope;
import com.dietsodasoftware.yail.oauth2.parse.DietSodaUtils;
import com.dietsodasoftware.yail.oauth2.parse.ParseAuthority;
import com.dietsodasoftware.yail.oauth2.parse.ParseConfiguration;
import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.client.YailProfile;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.model.Contact.Field;
import com.dietsodasoftware.yail.xmlrpc.model.InfusionsoftUserInfo;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftParameterValidationException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceGetUserInfoOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation.Compare;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * User: wendel.schultz
 * Date: 4/11/14
 */
public class Oauth2FlowDemo {

    public static void main(String[] args) throws IOException, OauthAuthenticationException, InterruptedException, InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {

        new Oauth2FlowDemo().authorize();

    }

    // authorize
    // https://signin.infusionsoft.com/app/oauth/authorize
    // http://tools.ietf.org/html/draft-ietf-oauth-v2-21#section-4.1.1
    public void authorize() throws IOException, OauthAuthenticationException, InterruptedException, InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        final long timeoutSeconds = 45;
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
                .withMaxAttempts(100)
                .build(parse);


        final InfusionsoftOauthAuthenticator auth = new InfusionsoftOauthAuthenticator();

        final InfusionsoftOauthToken token = auth.authorize2(parseAuthority, DietSodaUtils.MASHERY_CLIENT_ID, DietSodaUtils.MASHERY_CLIENT_SECRET, timeoutSeconds);

        // use this token as "API Key" in XMLRPC calls AND ?auth_token=<> URL parameter
        System.out.println("Got a auth token: " + token.getToken());

        if(token == null){
            //FAIL
        } else {
            final YailProfile profile = YailProfile.usingOAuth2Token(token);
            final YailClient client = profile.getClient();

            getUserInfo(client);

            queryContact(client);
        }
    }

    private void queryContact(YailClient client){
        final DataServiceQueryOperation<Contact> query = new DataServiceQueryOperation<Contact>(Contact.class)
                .fieldCompare(Field.Id, Compare.gt, "0")
                .setLimit(10)
                .orderBy(Field.LastName)
                .ascending();

        for(Contact contact: client.autoPage(query)){
            System.out.println("Contact: " + contact);
        }

    }

    private void getUserInfo(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        final DataServiceGetUserInfoOperation op = new DataServiceGetUserInfoOperation();

        final InfusionsoftUserInfo info = client.call(op);
        System.out.println("User info: " + info);
    }

    // token
    // https://api.infusionsoft.com/token
    // http://tools.ietf.org/html/draft-ietf-oauth-v2-21#section-4.1.3
    // do the biz
    // https://api.infusionsoft.com/crm/xmlrpc/v1?access_token=ACCESSTOKEN

}
