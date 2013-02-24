package com.dietsodasoftware.isft.xmlrpc;

import com.dietsodasoftware.isft.xmlrpc.client.IsftClient;
import com.dietsodasoftware.isft.xmlrpc.client.IsftProfile;
import com.dietsodasoftware.isft.xmlrpc.model.Contact;
import com.dietsodasoftware.isft.xmlrpc.model.ContactAction;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftFieldResults;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceAddOperation;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceDeleteOperation;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceFindByFieldOperation;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceQueryOperation;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceQueryOperation.Like;
import org.apache.xmlrpc.XmlRpcException;

import java.util.HashMap;
import java.util.Map;

public class WebServiceClientDriver {
	
	private static final String APP_NAME = "please put your app name here";
	private static final String API_KEY = "get your own tots";
	
	public static void main(String [] args) throws XmlRpcException{
		
		final IsftProfile profile = IsftProfile.usingApiKey(APP_NAME, API_KEY);
		final IsftClient client = profile.getClient();
		
		exerciseFindByQuery(client);
		exerciseFindByField(client);
		
		exerciseFindAppointments(client);
		
//		exerciseContactService();

        exerciseAddDataService(client);
        exerciseAddDataService(client);

        exerciseDeleteDataService(client);
    }
	
	private static void exerciseFindByQuery(IsftClient client) throws XmlRpcException {
		final DataServiceQueryOperation<Contact> finder = 
				new DataServiceQueryOperation<Contact>(Contact.class)
		             .fieldLike("FirstName", "A", Like.after)
//		             .addReturnFieldName(Contact.Field.Id)
//                     .addReturnFieldName(Contact.Field.DateCreated)
//                     .addReturnFieldName(Contact.Field.FirstName)
//                     .addReturnFieldName(Contact.Field.LastName)
                     ;

        System.out.println("FindByQuery: " );
        for(Contact contact: client.call(finder)){
        	System.out.println(contact);
        }
        
	}
	
	private static void exerciseFindByField(IsftClient client) throws XmlRpcException{
		final DataServiceFindByFieldOperation<Contact> findByDate = new DataServiceFindByFieldOperation<Contact>(Contact.class)
                     .setFieldCriteria(Contact.Field.Id, 41)
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
	
	private static void exerciseFindAppointments(IsftClient client) throws XmlRpcException{
		final DataServiceFindByFieldOperation<ContactAction> findByDate = new DataServiceFindByFieldOperation<ContactAction>(ContactAction.class)
                .setFieldCriteria(ContactAction.Field.IsAppointment, 1)
//                .addReturnFieldName(ContactAction.Field.Id)
//                .addReturnFieldName(ContactAction.Field.ActionDescription)
//                .addReturnFieldName(ContactAction.Field.ActionDate)
//                .addReturnFieldName(ContactAction.Field.EndDate)
//                .addReturnFieldName(ContactAction.Field.IsAppointment)
                ;

	   final InfusionsoftFieldResults<ContactAction> result = client.call(findByDate);
	
	   System.out.println("Appointment FindByDate: ");
	   for(ContactAction action: client.call(findByDate)){
		   System.out.println(action);
	   }
   
	}

    private static void exerciseAddDataService(IsftClient client) throws XmlRpcException {
        final Map<String, Object> contactData = new HashMap<String, Object>();
        contactData.put(Contact.Field.FirstName.name(), "WebServiceClientDriver");
        contactData.put(Contact.Field.LastName.name(), "DemoCode");

        final Contact contact = new Contact(contactData);
        final DataServiceAddOperation<Contact> add = new DataServiceAddOperation<Contact>(Contact.class)
                .useModelPrototype(contact);

        final Integer newId = client.call(add);
        System.out.println("The new Contact's ID: " + newId);
    }

    private static void exerciseDeleteDataService(IsftClient client) throws XmlRpcException {
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
}
