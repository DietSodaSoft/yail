package com.dietsodasoftware.yail.xmlrpc;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;
import com.dietsodasoftware.yail.xmlrpc.client.YailProfile;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.model.ContactAction;
import com.dietsodasoftware.yail.xmlrpc.model.TagAssignment;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftModelCollectionResults;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcException;
import com.dietsodasoftware.yail.xmlrpc.service.authentication.AuthenticationServiceAuthenticateUser;
import com.dietsodasoftware.yail.xmlrpc.service.contact.ContactServiceAddOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceAddOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceDeleteOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceFindByFieldOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceGetAppointmentCalOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceLoadOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation.Like;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WebServiceClientDriver {

    private static final String APP_PROPERTIES_NAME = "app.properties";
    private static final String PROP_APP_NAME = "com.dietsodasoftware.yail.appname";
    private static final String PROP_APP_APIKEY = "com.dietsodasoftware.yail.apikey";
    private static final String PROP_APP_LOCATION = "com.dietsodasoftware.yail.location";
    private static final String PROP_APP_USERNAME = "com.dietsodasoftware.yail.username";
    private static final String PROP_APP_PASSWORD = "com.dietsodasoftware.yail.password";

    public static void main(String [] args) throws InfusionsoftXmlRpcException, InfusionsoftResponseParsingException, IOException {

        final Properties props = readAppProperties();
        final String appName = props.getProperty(PROP_APP_NAME);
        final String apiKey = props.getProperty(PROP_APP_APIKEY + "." + appName);
        final String location = props.getProperty(PROP_APP_LOCATION + "." + appName);
        final String username = props.getProperty(PROP_APP_USERNAME + "." + appName);
        final String password = props.getProperty(PROP_APP_PASSWORD + "." + appName);

		final YailProfile profile;
        if(location == null){
            profile = YailProfile.usingApiKey(appName, apiKey);
        } else {
            profile = YailProfile.atLocationUsingApiKey(appName, location, apiKey);
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

        exerciseUsernamePasswordAuthentication(client, username, password);

        exerciseAddContactService(client);
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
		final DataServiceFindByFieldOperation<ContactAction> findByDate = new DataServiceFindByFieldOperation<ContactAction>(ContactAction.class)
                .setFieldCriteria(ContactAction.Field.IsAppointment, 1)
//                .addReturnFieldName(ContactAction.Field.Id)
//                .addReturnFieldName(ContactAction.Field.ActionDescription)
//                .addReturnFieldName(ContactAction.Field.ActionDate)
//                .addReturnFieldName(ContactAction.Field.EndDate)
//                .addReturnFieldName(ContactAction.Field.IsAppointment)
                ;

	   final InfusionsoftModelCollectionResults<ContactAction> result = client.call(findByDate);
	
	   System.out.println("Appointment FindByDate: ");
	   for(ContactAction action: client.call(findByDate)){
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
        final DataServiceLoadOperation<Contact, Contact> loader = new DataServiceLoadOperation<Contact, Contact>(Contact.class, 5);
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

    private static final void exerciseUsernamePasswordAuthentication(YailClient client, String username, String password) throws InfusionsoftXmlRpcException {
        System.out.print("Authentication using '" +username+ "'/'" +password+ "' : ");
        final AuthenticationServiceAuthenticateUser auth = new AuthenticationServiceAuthenticateUser(username, password);
        final Integer authenticatedUserId = client.call(auth);
        System.out.println("ID " + authenticatedUserId);
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
        contactData.put(Contact.Field.Email.name(),"icey502@gmail.com");
        contactData.put(Contact.Field.Company.name(),"Acme Rockets Inc");

        final Contact contact = new Contact(contactData);
        final ContactServiceAddOperation add = new ContactServiceAddOperation(contact);

        final Integer newId = client.call(add);
        System.out.println("The new Contact's ID: " + newId);
    }
}