package com.dietsodasoftware.isft.xmlrpc;

import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftFieldResults;
import org.apache.xmlrpc.XmlRpcException;

import com.dietsodasoftware.isft.xmlrpc.client.IsftClient;
import com.dietsodasoftware.isft.xmlrpc.client.IsftProfile;
import com.dietsodasoftware.isft.xmlrpc.model.Contact;
import com.dietsodasoftware.isft.xmlrpc.model.ContactAction;
import com.dietsodasoftware.isft.xmlrpc.service.contact.ContactService;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceFindByFieldOperation;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceQueryOperation;
import com.dietsodasoftware.isft.xmlrpc.service.data.DataServiceQueryOperation.Like;

public class WebServiceClientDriver {
	
	private static final String APP_NAME = "zt119";
	private static final String API_KEY = "bb553e1b31910a4dc923c80963aa3040be2ce59e7f332b873148a1c53651067f";
	
	public static void main(String [] args) throws XmlRpcException{
		
		final IsftProfile profile = IsftProfile.usingApiKey(APP_NAME, API_KEY);
		final IsftClient client = profile.getClient();
		
		exerciseFindByQuery(client);
		exerciseFindByField(client);
		
		exerciseFindAppointments(client);
		
//		exerciseContactService();
		
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
                     .setFieldName(Contact.Field.Id)
                     .setFieldValue(41)
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
                .setFieldName(ContactAction.Field.IsAppointment)
                .setFieldValue(Boolean.TRUE)
                .addReturnFieldName(ContactAction.Field.Id)
                .addReturnFieldName(ContactAction.Field.ActionDescription)
                .addReturnFieldName(ContactAction.Field.ActionDate)
                .addReturnFieldName(ContactAction.Field.EndDate)
                .addReturnFieldName(ContactAction.Field.IsAppointment);

	   final InfusionsoftFieldResults<ContactAction> result = client.call(findByDate);
	
	   System.out.println("Appointment FindByDate: ");
	   for(ContactAction action: client.call(findByDate)){
		   System.out.println(action);
	   }
   
	}

	private static void exerciseContactService() throws XmlRpcException{
		final ContactService cs = new ContactService(APP_NAME, API_KEY);
		
		final int id = cs.findContactByEmail("francis.jones@infusionsoft.com");
		System.out.println("Contact has ID: " + id);
	}
	
}
