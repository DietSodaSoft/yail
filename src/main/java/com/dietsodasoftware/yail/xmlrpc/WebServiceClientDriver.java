package com.dietsodasoftware.yail.xmlrpc;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.client.YailProfile;
import com.dietsodasoftware.yail.xmlrpc.model.*;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionResults;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateForTemporaryKey;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateUser;
import com.dietsodasoftware.yail.xmlrpc.service.contact.ContactServiceAddOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceAddOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceDeleteOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceFindByFieldOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceGetAppointmentCalOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceLoadOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation.Like;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceUtils;
import com.dietsodasoftware.yail.xmlrpc.service.paging.AutoForwardPagingIterator;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingBound;
import com.dietsodasoftware.yail.xmlrpc.utils.InfusionsoftDateTimeService;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

public class WebServiceClientDriver {

    private static final String APP_PROPERTIES_NAME = "app.properties";
    private static final String PROP_APP_NAME = "com.dietsodasoftware.yail.appname";
    private static final String PROP_APP_APIKEY = "com.dietsodasoftware.yail.apikey";
    private static final String PROP_APP_LOCATION = "com.dietsodasoftware.yail.location";
    private static final String PROP_APP_USERNAME = "com.dietsodasoftware.yail.username";
    private static final String PROP_APP_PASSWORD = "com.dietsodasoftware.yail.password";
    private static final String PROP_DSS_VENDOR_KEY = "com.dietsodasoftware.yail.vendorkey";

    public static void main(String [] args) throws InfusionsoftXmlRpcException, InfusionsoftResponseParsingException, IOException {

        final Properties props = readAppProperties();
        final String appName = props.getProperty(PROP_APP_NAME);
        final String apiKey = props.getProperty(PROP_APP_APIKEY + "." + appName);
        final String location = props.getProperty(PROP_APP_LOCATION + "." + appName);
        final String username = props.getProperty(PROP_APP_USERNAME + "." + appName);
        final String password = props.getProperty(PROP_APP_PASSWORD + "." + appName);
        final String vendorKey = props.getProperty(PROP_DSS_VENDOR_KEY);

        final boolean useVendorKey = false;
		final YailProfile profile;
        if(useVendorKey){
            if(location == null){
                profile = YailProfile.usingVendorKey(appName, vendorKey, username, password);
            } else {
                profile = YailProfile.atLocationUsingVendorKey(appName, location, vendorKey, username, password);
            }
        } else {
            if(location == null){
                profile = YailProfile.usingApiKey(appName, apiKey);
            } else {
                profile = YailProfile.atLocationUsingApiKey(appName, location, apiKey);
            }
        }
		final YailClient client = profile.getClient();


		exerciseFindByQuery(client);
		exerciseFindByField(client);

		exerciseFindAppointments(client);

        exerciseAddDataService(client);
        exerciseAddDataService(client);

        exerciseDeleteDataService(client);

        exerciseDataServiceLoad(client);

        exerciseDataServiceGetAppointmentCal(client);

        exerciseUsernamePasswordAuthentication(client, vendorKey, username, password);

        exerciseAddContactService(client);
        exerciseAddProductService(client);
    }
	
	private static void exerciseFindByQuery(YailClient client) throws InfusionsoftXmlRpcException {
		final DataServiceQueryOperation<Contact> finder = 
				new DataServiceQueryOperation<Contact>(Contact.class)
		             .fieldLike(Contact.Field.FirstName, "A", Like.after)
//		             .addReturnFieldName(Contact.Field.Id)
//                     .addReturnFieldName(Contact.Field.DateCreated)
//                     .addReturnFieldName(Contact.Field.FirstName)
//                     .addReturnFieldName(Contact.Field.LastName)
                     ;

        System.out.println("FindByQuery: " );
        for(Contact contact: client.call(finder)){
        	System.out.println(contact);
        }

        final String theTag = "7/24 Conference";
        final DataServiceQueryOperation<TagAssignment> tagged =
                new DataServiceQueryOperation<TagAssignment>(TagAssignment.class)
                .fieldEquals(TagAssignment.Field.ContactGroup, theTag);


        System.out.println("From tag : " + theTag);
        for(TagAssignment assign: client.call(tagged)){
            System.out.println("\t" + assign);
        }
	}

	private static void exerciseFindByField(YailClient client) throws InfusionsoftXmlRpcException{
		final DataServiceFindByFieldOperation<Contact> findByDate = new DataServiceFindByFieldOperation<Contact>(Contact.class)
                     .setFieldCriteria(Contact.Field.Id, 20)
//                     .addReturnFieldName(Contact.Field.Id)
//                     .addReturnFieldName(Contact.Field.DateCreated)
//                     .addReturnFieldName(Contact.Field.FirstName)
//                     .addReturnFieldName(Contact.Field.LastName)
                     ;
        System.out.println("FindByField: ");
        for(Contact contact: client.call(findByDate)){
        	System.out.println(contact);
        }

	}
	
	private static void exerciseFindAppointments(YailClient client) throws InfusionsoftXmlRpcException{
        final InfusionsoftDateTimeService dts = new InfusionsoftDateTimeService();
        final Date theStartDate = new Date();
        theStartDate.setMonth(2);
        theStartDate.setDate(23);

        theStartDate.setHours(0);
        theStartDate.setMinutes(0);
        theStartDate.setSeconds(0);

        final Date theEndDate = new LocalDateTime(theStartDate).plusDays(1).toDate();

        final String theStartDateBinding = dts.dateAsServiceBindingValue(theStartDate, InfusionsoftDateTimeService.DateTimeBinding.Date);
        final String theEndDateBinding   = dts.dateAsServiceBindingValue(theEndDate, InfusionsoftDateTimeService.DateTimeBinding.DateTime);
        final String today = dts.todayAsBindingValue(TimeZone.getDefault(), InfusionsoftDateTimeService.DateTimeBinding.Date);

        System.out.println("Start date: " + theStartDateBinding);
        System.out.println("End date:   " + theEndDateBinding);
        System.out.println("Today:      " + today);

        final DataServiceQueryOperation<User> verify = new DataServiceQueryOperation<User>(User.class)
                .fieldEquals(User.Field.Id, "1")
                .addReturnFieldName(User.Field.LastName);

        final User u = client.call(verify).iterator().next();
        System.out.println("User: " + u.getStruct());

        final DataServiceQueryOperation<ContactAction> findByDate = new DataServiceQueryOperation<ContactAction>(ContactAction.class)
                .fieldEquals(ContactAction.Field.IsAppointment, 1)
//                .fieldCompare(ContactAction.Field.ActionDate, DataServiceQueryOperation.Compare.gt, theStartDateBinding)
//                .fieldCompare(ContactAction.Field.ActionDate, DataServiceQueryOperation.Compare.lt, theEndDateBinding)
                .fieldCompare(ContactAction.Field.ActionDate, DataServiceQueryOperation.Compare.gte, theStartDateBinding)
//                .addReturnFieldName(ContactAction.Field.Id)
//                .addReturnFieldName(ContactAction.Field.ActionDescription)
//                .addReturnFieldName(ContactAction.Field.ActionDate)
//                .addReturnFieldName(ContactAction.Field.EndDate)
//                .addReturnFieldName(ContactAction.Field.IsAppointment)
                .addReturnFieldName(ContactAction.Field.ActionDate)
                .addReturnFieldName(ContactAction.Field.ActionDescription)
                .addReturnFieldName(ContactAction.Field.IsAppointment)
                .setLimit(2)
                .orderBy(ContactAction.Field.ActionDate)
                .ascending()
                ;

        System.out.println("All-manual paging");
        boolean stop = false;
        boolean firstRequest = true;
        do {
            final DataServiceQueryOperation<ContactAction> nextRequest;
            if(firstRequest){
                firstRequest = false;
                nextRequest = findByDate;
            } else {
                nextRequest = findByDate.nextPage();
            }
            final InfusionsoftModelCollectionResults<ContactAction> result = client.call(nextRequest);

            if(result.length() < nextRequest.getLimit()){
                stop = true;
            }
            if(result.length() == 0){
                continue;
            }

            System.out.println("Appointment FindByDate: ");
            for(ContactAction action: result){
                System.out.println(action);
            }

        } while (stop == false);


        final Date before4 = (Date) theStartDate.clone();
        before4.setHours(14);
        final ForwardPagingBound<ContactAction> boundary = new ForwardPagingBound<ContactAction>() {
            @Override
            public boolean isInBounds(ContactAction model) {
                final Date apptDate = model.getFieldValue(ContactAction.Field.ActionDate);
                if(apptDate == null){
                    return false;
                } else {
                    return apptDate.before(before4);
                }
            }
        };

        final Iterator<ContactAction> it1 = DataServiceUtils.dataServiceQueryOperationForwardIteratorWithBoundaryFor(client, findByDate, boundary);

        System.out.println("IT1: boundary paging");
        stop = false;
        while(stop == false && it1.hasNext()){
            final ContactAction action = it1.next();
            System.out.println(action);
        }

//        final AutoForwardPagingIterator<ContactAction, DataServiceQueryOperation<ContactAction>> pager = new AutoForwardPagingIterator<ContactAction, DataServiceQueryOperation<ContactAction>>(client, findByDate);
//        final Iterator<ContactAction> iterator = pager.iterator();
        final Iterator<ContactAction> it2 = DataServiceUtils.dataServiceQueryOperationForwardIteratorFor(client, findByDate);

        System.out.println("IT2: no boundary - endless pages");
        stop = false;
        while(stop == false && it2.hasNext()){
            final ContactAction action = it2.next();
            System.out.println(action);
        }
	}

    private static void exerciseAddDataService(YailClient client) throws InfusionsoftXmlRpcException {
        final Map<String, Object> contactData = new HashMap<String, Object>();
        contactData.put(Contact.Field.FirstName.name(), "WebServiceClientDriver");
        contactData.put(Contact.Field.LastName.name(), "DemoCode");

        final Contact contact = new Contact(contactData);
        final DataServiceAddOperation<Contact> add = new DataServiceAddOperation<Contact>(Contact.class)
                .useModelPrototype(contact);

        final Integer newId = client.call(add);
        System.out.println("The new Contact's ID: " + newId);
    }

    private static void exerciseDeleteDataService(YailClient client) throws InfusionsoftXmlRpcException {
        final DataServiceFindByFieldOperation<Contact> finder = new DataServiceFindByFieldOperation<Contact>(Contact.class)
                .addReturnFieldName(Contact.Field.Id)
                .setFieldCriteria(Contact.Field.LastName, "DemoCode")
                ;

        for(Contact contact: client.call(finder)){
            final Integer id = contact.getFieldValue(Contact.Field.Id);
            final DataServiceDeleteOperation<Contact> delete = new DataServiceDeleteOperation<Contact>(Contact.class, id);
            final Boolean deleted = client.call(delete);
            System.out.println("Success deleting id '" +id+ "': " + deleted);
        }
    }

    private static void exerciseDataServiceLoad(YailClient client) throws InfusionsoftXmlRpcException {
        final DataServiceLoadOperation<Contact, Contact> loader = new DataServiceLoadOperation<Contact, Contact>(Contact.class, 207);
        final Contact contact = client.call(loader);
        System.out.println("Loaded Contact: " + contact);
    }

    private static void exerciseDataServiceGetAppointmentCal(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftResponseParsingException {
        final DataServiceGetAppointmentCalOperation cal = new DataServiceGetAppointmentCalOperation(1);
        final String response = client.call(cal);
        final Calendar appt = DataServiceGetAppointmentCalOperation.asIcal4jCalendar(response);
        System.out.println("Appointment cal: ");
        System.out.println(appt);
    }

    private static final void exerciseUsernamePasswordAuthentication(YailClient client, String vendorKey, String username, String password) throws InfusionsoftXmlRpcException {
        System.out.print("Authentication using '" +username+ "'/'" +password+ "'/" +client.getKey()+" : ");
        final AuthenticationServiceAuthenticateUser auth = new AuthenticationServiceAuthenticateUser(username, password);
        final Integer authenticatedUserId = client.call(auth);
        System.out.println("ID " + authenticatedUserId);

        final AuthenticationServiceAuthenticateForTemporaryKey keyAuth = new AuthenticationServiceAuthenticateForTemporaryKey(vendorKey, username, password);
        final String temporaryKey = client.call(keyAuth);
        System.out.println("Temporary key: " + temporaryKey);
    }

    private static final Properties readAppProperties() throws IOException {
        final Properties props = new Properties();
        final InputStream is = WebServiceClientDriver.class.getClassLoader().getResourceAsStream(APP_PROPERTIES_NAME);
        props.load(is);

        return props;
    }

    private static void exerciseAddContactService(YailClient client) throws InfusionsoftXmlRpcException {
        final Map<String, Object> contactData = new HashMap<String, Object>();
        contactData.put(Contact.Field.FirstName.name(), "WebServiceClientDriverDemoFirstName");
        contactData.put(Contact.Field.LastName.name(), "DemoLastName");
        contactData.put(Contact.Field.Email.name(),"whatever@whatever.com");
        contactData.put(Contact.Field.Company.name(),"Acme Rockets Inc");

        final Contact contact = new Contact(contactData);
        final ContactServiceAddOperation add = new ContactServiceAddOperation(contact);

        final Integer newId = client.call(add);
        System.out.println("The new Contact's ID: " + newId);
    }

    private static void exerciseAddProductService(YailClient client) throws InfusionsoftXmlRpcException {
        final Map<String, Object> productData = new HashMap<String, Object>();
        productData.put(Product.Field.ProductName.name(), "TestProduct1");
        productData.put(Product.Field.ProductPrice.name(), "2.99");

        final Product product = new Product(productData);
        final DataServiceAddOperation<Product> add = new DataServiceAddOperation<Product>(Product.class)
                .useModelPrototype(product);

        final Integer newId = client.call(add);
        System.out.println("The new Product's ID: " + newId);
    }
}
