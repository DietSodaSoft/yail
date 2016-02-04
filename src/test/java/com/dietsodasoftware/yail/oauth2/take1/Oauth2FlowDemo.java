package com.dietsodasoftware.yail.oauth2.take1;

import com.dietsodasoftware.yail.oauth2.client.InfusionsoftOauthToken;
import com.dietsodasoftware.yail.oauth2.take1.client.InfusionsoftOauthAuthenticator;
import com.dietsodasoftware.yail.oauth2.take1.client.OauthAuthenticationException;
import com.dietsodasoftware.yail.oauth2.client.scopes.Scope;
import com.dietsodasoftware.yail.oauth2.take1.parse.DietSodaUtils;
import com.dietsodasoftware.yail.oauth2.take1.parse.ParseAuthority;
import com.dietsodasoftware.yail.oauth2.take1.parse.ParseConfiguration;
import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.client.YailProfile;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.model.Contact.Field;
import com.dietsodasoftware.yail.xmlrpc.model.ContactAction;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftParameterValidationException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryFilter;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryFilter.Builder.Like;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;

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

        // use this token as "API Key" in XMLRPC calls AND ?auth_token=<> URL parameter
        final InfusionsoftOauthToken token = auth.authorize2(parseAuthority, DietSodaUtils.MASHERY_CLIENT_ID, DietSodaUtils.MASHERY_CLIENT_SECRET, timeoutSeconds);

        if(token == null || token.getToken() == null){
            System.err.println("Did not get a token");
        } else {
            System.out.println("Got a auth token: " + token.getToken().substring(0, 5) + "...");
            final YailProfile profile = YailProfile.usingOAuth2Token(token);
            final YailClient client = profile.getClient();

            queryContact(client);
        }
    }

    private void queryContact(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {

        // Exercise: find all contacts with a first name starting with "B" and get thier appointments ordered by start date
        final DataServiceQueryFilter<Contact> modelFilter;

        modelFilter = DataServiceQueryFilter.builder(Contact.class)
                .fieldLike(Field.FirstName, "B", Like.after)
                .fieldLike(Field.LastName, "D", Like.surrounding)
                .fieldIsNull(Field.Address1Type)
                .build();

        final Integer contactCount = client.call(modelFilter.count());

        System.out.print("Count: " + contactCount);
        final DataServiceQueryOperation<Contact> query = modelFilter.query()
                .setLimit(30)
                .ascending()
                .orderBy(Field.AccountId);

        final DataServiceQueryOperation<Contact> page2 = query.nextPage();

        for(Contact contact: client.autoPage(query)){

            final DataServiceQueryFilter<ContactAction> appointments;
            appointments = DataServiceQueryFilter.builder(ContactAction.class)
                    .fieldEquals(Field.Id, contact.getFieldValue(Field.Id))
                    .build();
            for (ContactAction appt: client.autoPage(appointments.query())){
                System.out.println("       Contact appt: " + appt );
            }
        }
    }

}
