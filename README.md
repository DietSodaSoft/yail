# YAIL:  Yet Another Infusionsoft Library
*This library is not affiliated with nor supported by Infusionsoft. Direct any questions to me or create an issue here.*

This project is an attempt to be an easy-to-use client library for [Infusionsoft's API][1].

[1]: http://help.infusionsoft.com/api-docs


Using the Infusionsoft APIs isn't all that bad.  *Using XmlRpc is what is painful.*  **"Struct."**

There are a few basic building blocks:

*  The Profile
*  The Client
*  The Models
*  The Web Service Operation

###The Profile.

The profile is the Infusionsoft app location and whatever you need to authenticate against that app in order to create a
valid client.

Currently, there are three ways to authenticate:

*  Using an app user's username/password (not currently supported yet)
*  Using a vendor's key (not currently supported yet)
*  Using an app's API key (supported because it is easiest)

A profile is meant to be stored securely in the app somewhere so that a user provides config once and they are good to
go.


### The Client.

The client sends a valid, signed XmlRpc request to the Infusionsoft app after asking the operation for it's parameters.  Additionally,
the client will ask the operation to unmarshall the results.


### The Models.

The Infusionsoft APIs vend data about real-world things: contacts, opportunities, tasks, appointments, etc.  Wouldn't
it be nice if my application can think about these real-world things and not bags key/value pairs?  That's what a
model is, in case you've never written any code in your entire life.


### The Web Service Operation

The web service operation thinks about two things: what it sends and what is returned.  That's it.  The operation
provides semantic clarity into what the operation does, but not necessarily how (only if necessary).  The operation
makes it painfully obvious what you can hope to get back, assuming nothing goes wrong.



##ENOUGH FOREPLAY!!  Let's see this thing in action.

The heart and soul of how this works is **`IsftClient.call()`**.  It is really simple:

	public <T> T call(InfusionsoftXmlRpcServiceOperation<T> operation) throws XmlRpcException{

		// TODO: operation.validateRequest();  Let the operation throw if it hasn't been set up with valid args
		final List<?> parameters = operation.getXmlRpcParameters(this);

		final Object rawResult = infusionApp.execute(operation.getRpcName(), parameters);
		final T parsedResult = operation.parseResult(rawResult);

		return parsedResult;
	}

## DataService.

The DataService has a few basic request types:

* Operations return primitives (boolean and integer)
* Operations return Models
* Operations return collections of Models

For operations which return models or collections models, you can explicitly assemble the fields you want returned, or you can do nothing
and you'll get them all, but NOT custom fields.  To get custom fields, you must request them explicitly.

For the operations which return collections of models, you can page through them by manually
setting the page number, etc - OR - doing client.call(operation.next()).  You can set the page limit size, the
fields you want returned, which page you want on the request operation.

These operations are pretty dumb, though.  For example, if you try to page before page 0, you'll get page 0 again.  Also,
you can beat the off-by-one by checking to see if you received as many records as your page size is large, and not
asking for the next page in the event the record count is less than page size.



This is stuff stripped from WebServiceDriver class.

### DataService.add

This an easy DataService to use.  Give it a model and it saves it (currently creating a model is somewhat awkward, but
is low-hanging fruit to improve):

          final Map<String, Object> contactData = new HashMap<String, Object>();
          contactData.put(Contact.Field.FirstName.name(), "Lennard");
          contactData.put(Contact.Field.LastName.name(), "Wagner");

          final Contact contact = new Contact(contactData);
          final DataServiceAddOperation<Contact> add = new DataServiceAddOperation<Contact>(Contact.class)
                  .useModelPrototype(contact);

          final Integer newId = client.call(add);
          System.out.println("The new Contact's ID: " + newId);


### DataService.load

This is also pretty easy.  Give it a model and an Id, and it returns the selected fields.

        final DataServiceLoadOperation<Contact, Contact> loader = new DataServiceLoadOperation<Contact, Contact>(Contact.class, 5);
        final Contact contact = client.call(loader);
        System.out.println("Loaded Contact: " + contact);


### DataService.delete

This is also pretty easy.  Give it a model and an Id, and it gives you a boolean if it deleted successfully or not:

            final Integer id = 37; // whatever ...
            final DataServiceDeleteOperation<Contact> delete = new DataServiceDeleteOperation<Contact>(Contact.class, id);
            final Boolean deleted = client.call(delete);
            System.out.println("Success deleting id '" +id+ "': " + deleted);


### DataService.findByField

Unlike those discussed above, this returns a collection of Models.  Provide it with a field and the value the field
should match in the query.   The usual return fields, page number, page limit and returned collection applies.

        final DataServiceFindByFieldOperation<Contact> finder = new DataServiceFindByFieldOperation<Contact>(Contact.class)
                .addReturnFieldName(Contact.Field.Id)
                .setFieldCriteria(Contact.Field.LastName, "DemoCode")  // Use any of the Model's fields, which implement NamedField
                ;

        for(Contact contact: client.call(finder)){
            System.out.println("We found a model: " + contact);
        }

Contacts aren't the only show in town.  Search for Appointments:

		final DataServiceFindByFieldOperation<ContactAction> appt = new DataServiceFindByFieldOperation<ContactAction>(ContactAction.class)
                .setFieldCriteria(ContactAction.Field.IsAppointment, 1)

	   final InfusionsoftFieldResults<ContactAction> result = client.call(appt);

	   System.out.println("Appointment Find: ");
	   for(ContactAction action: client.call(findByDate)){
		   System.out.println(action);
	   }

Currently, models include: Campaignee, CampaignStep, Company, Contact, ContactAction (task and appointments), GroupAssign, Lead, MtgLead, ProductInterest, ProductInterestBundle, Stage, StageMove, Status, User, and UserGroup.

### DataService.query

Query is a bit like findByField, but much more general.  You can cascade restrictions on different fields, even including
a primitive 'like' syntax.  The usual return fields, page number, page limit and returned collection applies.

        // find all contacts with a first name starting with 'A'
		final DataServiceQueryOperation<Contact> finder =
				new DataServiceQueryOperation<Contact>(Contact.class)
		             .fieldLike("FirstName", "A", Like.after);

        System.out.println("FindByQuery: " );
        for(Contact contact: client.call(finder)){
            System.out.println(contact);
        }


### DataService.getAppointmentCal

Very simple.  For an appointment ID, get the Cal entry for it as a string.  As a added convenience, there are
easily-used methods to parse the response into ical4j's Calendar.

        final DataServiceGetAppointmentCalOperation cal = new DataServiceGetAppointmentCalOperation(39);
        final String response = client.call(cal);
        final Calendar appt = DataServiceGetAppointmentCalOperation.asIcal4jCalendar(response);

        System.out.println("Appointment cal: ");
        System.out.println(appt);

I don't completely know what is going on with this one.  I have requested an iCal for several valid appointments, but only one of them succeeds.  I get `XmlRpcException`s for the others.  I'm working on understanding the nuances of this API call.
