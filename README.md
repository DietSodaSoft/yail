# YAIL:  Yet Another Infusionsoft Library
*This library is not affiliated with nor supported by Infusionsoft. Direct any questions to me or create an issue here.
[wschultz@dietsodasoftware.com](mailto:wschultz@dietsodasoftware.com)*

This project is an attempt to be an easy-to-use client library for [Infusionsoft's API][1].

[1]: http://help.infusionsoft.com/api-docs


Using the Infusionsoft APIs isn't all that bad.  *Using XmlRpc is what is painful.*  **"Struct."**

## What's new?
Check out the [Release Notes](wiki/Release Notes) for what has been happening lately.

## Getting Started

You can get right to work using version 0.7.13 or greater by including it in your maven project:

        <repositories>
            ...
            <repository>
                <id>dss-maven-repo</id>
                <name>Diet Soda Software Repository for YAIL</name>
                <url>http://maven.dietsodasoftware.com/maven2</url>
            </repository>
            ...
        </repositories>
        ...
        <dependencies>
           ...
                   <dependency>
                       <groupId>com.dietsodasoftware</groupId>
                       <artifactId>yail-XmlRpcJavaClient</artifactId>
                       <version>${com.dietsodasoftware.yail-version}</version>
                   </dependency>
           ...
        </dependencies>
        ...
        <properties>
            ...
            <com.dietsodasoftware.yail-version>0.9.0</com.dietsodasoftware.yail-version>
            ...
        </properties>

## One-Pager on design, usage and reference
There are a few basic building blocks:

*  The Profile ([design](wiki/design/Profiles) / [usage](wiki/usage/Profiles))
*  The Client ([design](wiki/design/Clients) / [usage](wiki/usage/Clients))
*  The Models ([design](wiki/design/Models) / [usage](wiki/usage/Models)) / [reference](wiki/reference/Models))
*  The Web Service Operation ([design](wiki/design/Operations) / [usage](wiki/usage/Operations) / [reference](wiki/reference/Operations)
*  A few Utilities ([reference](wiki/reference/Utilities))
*  Infusionsoft authentication ([design](wiki/design/Authentication))

Read more about [YAIL's design](wiki/design/Home) in the wiki.

Read more about [YAIL's usage documentation](wiki/usage/Home) in the wiki.

Read more about [YAIL's reference documentation](wiki/reference/Home) in the wiki.

###The Profile.

The profile is the Infusionsoft app location and whatever you need to authenticate against that app in order to create a
valid client.

Currently, there are several ways to authenticate:

*  Using an app user's username/password
*  Using a vendor's key
*  Using an app's API key
*  Using OAuth (more documentation forthcoming)

A profile is meant to be stored securely in the app somewhere so that a user provides config once and they are good to
go by virtue of using the profile as a factory for YailClients which are able to connect using the appropriate tokens.

Read more in the [wiki](wiki/design/Profiles).

### The Client.

The client sends a valid, signed XmlRpc request to the Infusionsoft app after asking the operation for it's parameters.  Additionally,
the client will ask the operation to unmarshall the results.

Read more in the [wiki](wiki/design/Clients).

### The Models.

The Infusionsoft APIs vend data about real-world things: contacts, opportunities, tasks, appointments, etc.  Wouldn't
it be nice if my application can think about these real-world things and not bags of key/value pairs?  That's what a
model is, in case you've never written any code in your entire life.

Read more in the [wiki](wiki/design/Models).

### The Web Service Operation

The web service operation thinks about two things: what it sends and what is returned.  That's it.  The operation
provides semantic clarity into what the operation does, but not necessarily how (only if necessary).  The operation
makes it painfully obvious what you can hope to get back, assuming nothing goes wrong.

Read more in the [wiki](wiki/design/Operations).


##ENOUGH FOREPLAY!!  Let's see this thing in action.

The heart and soul of how this works is **`YailClient.call()`**.  It is really simple; the crux of the implementation looks like this:

	public <T> T call(InfusionsoftXmlRpcServiceOperation<T> operation) throws XmlRpcException{

		final List<?> parameters = operation.getXmlRpcParameters(this);

        // infusionApp is a configured apache XmlRpcClient instance.
		final Object rawResult = infusionApp.execute(operation.getRpcName(), parameters);
		final T parsedResult = operation.parseResult(rawResult);

		return parsedResult;
	}

A few prototypical usage demos follow.

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
asking for the next page in the event the record count is less than page size.  However, we recommend
using [client.autoPage()](wiki/usage/Clients).



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

Currently, well over half of the models are codified and are still under construction.

### DataService.query

Query is a bit like findByField, but much more powerful.  You can cascade restrictions on different fields using a
DataServiceQueryFilter, even including a primitive 'like' syntax.  The filter object can then provide for you a Query
operation to then appropriately set the usual return fields, page number, page limit and returned collection applies.
Alternatively, the same filter object can return a Operation which will return an integer: the total count of records
matching that filter.  This is useful to know how many "pages" to expect.

        // find all contacts with a first name starting with 'A'
        final DataServiceQueryFilter<Contact> filter = DataServiceQueryFilter.builder(Contact.class)
		             .fieldLike(Contact.Field.FirstName, "A", Like.after)
		             .customFieldLike("DogName", "Ral", Like.after)
                     .build();

        final Integer count = client.call(filter.count());
        System.out.println("FindByQuery count: " + count);

		final DataServiceQueryOperation<Contact> finder = filter.query();

        System.out.println("FindByQuery: " );
        for(Contact contact: client.autoPage(finder)){
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

This API call has a bug in it, at the time of writing.  If you request an iCal for a contact which doesn't have an email
address, you will get an XmlRpcException with little to no hint as to what is going on.