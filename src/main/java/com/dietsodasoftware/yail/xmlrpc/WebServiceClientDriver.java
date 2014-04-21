package com.dietsodasoftware.yail.xmlrpc;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.client.YailProfile;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.model.Contact.Field;
import com.dietsodasoftware.yail.xmlrpc.model.ContactAction;
import com.dietsodasoftware.yail.xmlrpc.model.CustomField;
import com.dietsodasoftware.yail.xmlrpc.model.Product;
import com.dietsodasoftware.yail.xmlrpc.model.TagAssignment;
import com.dietsodasoftware.yail.xmlrpc.model.User;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionResults;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftParameterValidationException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateForTemporaryKey;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateUser;
import com.dietsodasoftware.yail.xmlrpc.service.contact.ContactServiceAddOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceAddOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceDeleteOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceFindByFieldOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceGetAppSettingOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceGetAppointmentCalOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceLoadOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryCountOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryFilter;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryFilter.Builder.Compare;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryFilter.Builder.Like;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceUpdateCustomFieldOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceUpdateOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceUtils;
import com.dietsodasoftware.yail.xmlrpc.service.paging.ForwardPagingBound;
import com.dietsodasoftware.yail.xmlrpc.utils.InfusionsoftDateTimeService;
import net.fortuna.ical4j.model.Calendar;
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
    private static final String PROP_DSS_VENDOR_KEY = "com.dietsodasoftware.yail.vendorkey";
    private static final String PROP_CAS_USERNAME_KEY = "com.dietsodasoftware.yail.cas-username";
    private static final String PROP_CAS_PASSWORD_KEY = "com.dietsodasoftware.yail.cas-password";

    public static void main(String [] args) throws InfusionsoftXmlRpcException, InfusionsoftResponseParsingException, IOException, InfusionsoftParameterValidationException {

        final Properties props = readAppProperties();
        final String appName = props.getProperty(PROP_APP_NAME);
        final String apiKey = props.getProperty(PROP_APP_APIKEY + "." + appName);
        final String location = props.getProperty(PROP_APP_LOCATION + "." + appName);
        final String casUsername = props.getProperty(PROP_CAS_USERNAME_KEY);
        final String casPassword = props.getProperty(PROP_CAS_PASSWORD_KEY);
        final String vendorKey = props.getProperty(PROP_DSS_VENDOR_KEY);

        final boolean useVendorKey = false;
		final YailProfile profile;
        if(useVendorKey){
            if(location == null){
                profile = YailProfile.usingVendorKey(appName, vendorKey, casUsername, casPassword);
            } else {
                profile = YailProfile.atLocationUsingVendorKey(appName, location, vendorKey, casUsername, casPassword);
            }
        } else {
            if(location == null){
                profile = YailProfile.usingApiKey(appName, apiKey);
            } else {
                profile = YailProfile.atLocationUsingApiKey(appName, location, apiKey);
            }
        }
		final YailClient client = profile.getClient();

        exerciseUpdate(client, true);

        exerciseVendorKeyToken(client, vendorKey, casUsername, casPassword);

        exerciseUpdateCustomField(client);

        exerciseAppSettings(client);

        exerciseFindByQuery(client);
		exerciseFindByField(client);

        exerciseFindAppointments(client);

        exerciseAddDataService(client);
        exerciseAddDataService(client);

        exerciseDeleteDataService(client);

        exerciseDataServiceLoad(client);

        exerciseDataServiceGetAppointmentCal(client);

        exerciseUsernamePasswordAuthentication(client, vendorKey, casUsername, casPassword);

        exerciseAddContactService(client);
        exerciseAddProductService(client);
    }

    private static void exerciseUpdate(final YailClient client, boolean commit) throws InfusionsoftParameterValidationException, InfusionsoftXmlRpcException {
        final int id = 333;
        final String newName = "Carrots";

        final CustomField updates = CustomField.builder()
                .setFieldValue(CustomField.Field.Label, newName)
                .build();

        final DataServiceUpdateOperation update = new DataServiceUpdateOperation(id, updates);

        if(commit){
            final Integer updated = client.call(update);
            System.out.println("Updated field: " + updated + ", was " + id);
        }

        final DataServiceLoadOperation<CustomField, CustomField> loader = new DataServiceLoadOperation<CustomField, CustomField>(CustomField.class, id);
        final CustomField loaded = client.call(loader);
        System.out.println("Load custom field: " + loaded);
    }


    private static void exerciseUpdateCustomField(YailClient client) throws InfusionsoftParameterValidationException, InfusionsoftXmlRpcException {
        final int fieldId = 124;
        final String newName = "Carrots";

        final CustomField updates = CustomField.builder()
                .setFieldValue(CustomField.Field.Label, newName)
                .build();

        final DataServiceUpdateCustomFieldOperation updater = new DataServiceUpdateCustomFieldOperation(fieldId, updates);

        final Boolean success = client.call(updater);
        System.out.println("Update custom field:  " + success);

        final DataServiceLoadOperation<CustomField, CustomField> loader = new DataServiceLoadOperation<CustomField, CustomField>(CustomField.class, fieldId);
        final CustomField loaded = client.call(loader);
        System.out.println("Load custom field: " + loaded);
    }


    private static void exerciseAppSettings(YailClient client) throws InfusionsoftParameterValidationException, InfusionsoftXmlRpcException {
        final DataServiceGetAppSettingOperation appSetting = new DataServiceGetAppSettingOperation("Email", "appcity");
        final String emailCity = client.call(appSetting);

        System.out.println("The email city is: " + emailCity);
    }

    private static void exerciseVendorKeyToken(YailClient client, String vendorKey, String username, String password) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        final AuthenticationServiceAuthenticateForTemporaryKey keyAuth = new AuthenticationServiceAuthenticateForTemporaryKey(vendorKey, username, password);
        final String key =  client.call(keyAuth);

        System.out.println("Temporary key: " + key);

    }

	private static void exerciseFindByQuery(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        final DataServiceQueryFilter<Contact> filter = DataServiceQueryFilter.builder(Contact.class)
                .fieldLike(Contact.Field.FirstName, "A", Like.after)
                .build()
                ;
		final DataServiceQueryOperation<Contact> finder = 
				new DataServiceQueryOperation<Contact>(filter);
//		             .addReturnFieldName(Contact.Field.Id)
//                     .addReturnFieldName(Contact.Field.DateCreated)
//                     .addReturnFieldName(Contact.Field.FirstName)
//                     .addReturnFieldName(Contact.Field.LastName)
                     ;

        final DataServiceQueryCountOperation<Contact> counter = new DataServiceQueryCountOperation<Contact>(filter);

        final Integer count = client.call(counter);
        System.out.println("Query identifies " + count + " contacts");

        System.out.println("FindByQuery: " );
        for(Contact contact: client.call(finder)){
        	System.out.println(contact);
        }

        final String theTag = "7/24 Conference";
        final DataServiceQueryFilter<TagAssignment> tagFilter = DataServiceQueryFilter.builder(TagAssignment.class)
                .fieldEquals(TagAssignment.Field.ContactGroup, theTag)
                .build()
                ;
        final DataServiceQueryOperation<TagAssignment> tagged =
                new DataServiceQueryOperation<TagAssignment>(tagFilter);


        System.out.println("From tag : " + theTag);
        for(TagAssignment assign: client.call(tagged)){
            System.out.println("\t" + assign);
        }
	}

	private static void exerciseFindByField(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
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
	
	private static void exerciseFindAppointments(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
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

        final DataServiceQueryFilter<User> userFilter = DataServiceQueryFilter.builder(User.class)
                .fieldEquals(User.Field.Id, "1")
                .build()
                ;
        final DataServiceQueryOperation<User> verify = new DataServiceQueryOperation<User>(userFilter)
                .addReturnFieldName(User.Field.LastName);

        final User u = client.call(verify).iterator().next();
        System.out.println("User: " + u.getStruct());

        final DataServiceQueryFilter<ContactAction> caFilter = DataServiceQueryFilter.builder(ContactAction.class)
                .fieldEquals(ContactAction.Field.IsAppointment, 1)
//                .fieldCompare(ContactAction.Field.ActionDate, Compare.gt, theStartDateBinding)
//                .fieldCompare(ContactAction.Field.ActionDate, Compare.lt, theEndDateBinding)
                .fieldCompare(ContactAction.Field.ActionDate, Compare.gte, theStartDateBinding)
                .build()
                ;
        final DataServiceQueryOperation<ContactAction> findByDate = new DataServiceQueryOperation<ContactAction>(caFilter)
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

    private static void exerciseAddDataService(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        final Map<String, Object> contactData = new HashMap<String, Object>();
        contactData.put(Contact.Field.FirstName.name(), "WebServiceClientDriver");
        contactData.put(Contact.Field.LastName.name(), "DemoCode");

        final Contact contact = new Contact(contactData);
        final DataServiceAddOperation<Contact> add = new DataServiceAddOperation<Contact>(Contact.class)
                .useModelPrototype(contact);

        final Integer newId = client.call(add);
        System.out.println("The new Contact's ID: " + newId);
    }

    private static void exerciseDeleteDataService(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
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

    private static void exerciseDataServiceLoad(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        final DataServiceLoadOperation<Contact, Contact> loader = new DataServiceLoadOperation<Contact, Contact>(Contact.class, 207);
        final Contact contact = client.call(loader);
        System.out.println("Loaded Contact: " + contact);
    }

    private static void exerciseDataServiceGetAppointmentCal(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftResponseParsingException, InfusionsoftParameterValidationException {
        final DataServiceGetAppointmentCalOperation cal = new DataServiceGetAppointmentCalOperation(1);
        final String response = client.call(cal);
        final Calendar appt = DataServiceGetAppointmentCalOperation.asIcal4jCalendar(response);
        System.out.println("Appointment cal: ");
        System.out.println(appt);
    }

    private static final void exerciseUsernamePasswordAuthentication(YailClient client, String vendorKey, String username, String password) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        System.out.print("Authentication using '" +username+ "'/'" +password+ "'/" +client.getKey()+" : ");
        final AuthenticationServiceAuthenticateUser auth = AuthenticationServiceAuthenticateUser.usingPlainTextPassword(username, password);
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

    private static void exerciseAddContactService(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
        final Map<String, Object> contactData = new HashMap<String, Object>();
        contactData.put(Contact.Field.FirstName.name(), "WebServiceClientDriverDemoFirstName");
        contactData.put(Contact.Field.LastName.name(), "DemoLastName");
        contactData.put(Contact.Field.Email.name(), "whatever@whatever.com");
        contactData.put(Contact.Field.Company.name(),"Acme Rockets Inc");

        final Contact contact = new Contact(contactData);
        final ContactServiceAddOperation add = new ContactServiceAddOperation(contact);

        final Integer newId = client.call(add);
        System.out.println("The new Contact's ID: " + newId);
    }

    private static void exerciseAddProductService(YailClient client) throws InfusionsoftXmlRpcException, InfusionsoftParameterValidationException {
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
