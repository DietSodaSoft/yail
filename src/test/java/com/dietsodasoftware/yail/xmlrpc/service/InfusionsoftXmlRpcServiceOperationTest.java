package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/24/13
 * Time: 1:49 AM
 */
public class InfusionsoftXmlRpcServiceOperationTest {

    private static class ContactQuery extends DataServiceQueryOperation<Contact>{

        public ContactQuery() {
            super(Contact.class);
        }
    }

    @Test
    public void testOperationSubclassesInheritAnnotationData(){

        new ContactQuery();
    }
}
