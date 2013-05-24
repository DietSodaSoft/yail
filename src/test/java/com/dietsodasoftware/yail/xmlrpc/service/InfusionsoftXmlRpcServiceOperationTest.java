package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.ArgumentValidator;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.model.ContactAction;
import com.dietsodasoftware.yail.xmlrpc.service.data.DataServiceQueryOperation;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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

    private static class ContactActionQuery extends DataServiceQueryOperation<ContactAction>{
        public ContactActionQuery(){
            super(ContactAction.class);
        }

        @ArgumentValidator
        public void contactActionValidate() throws InfusionsoftParameterValidationException {
            throw new InfusionsoftParameterValidationException("Just because");
        }
    }

    private static class FancyContactActionQuery extends ContactActionQuery {

    }

    @Test
    public void testOperationSubclassesInheritAnnotationData(){
        new ContactQuery();
    }

    @Test
    public void testArgumentValidatorMethodIsNotNeededButValidationIsAttempted() throws InfusionsoftParameterValidationException {
        final ContactQuery eq = spy(new ContactQuery());

        eq.validateArguments();

    }

    @Test(expected = InfusionsoftParameterValidationException.class)
    public void testArgumentValidatorMethodFromParentClassIsCalled() throws InfusionsoftParameterValidationException {
        final FancyContactActionQuery eq = new FancyContactActionQuery();

        eq.validateArguments();

    }

    @Test(expected = InfusionsoftParameterValidationException.class)
    public void testArgumentValidatorMethodIsCalledFromDeclaringSubclass() throws InfusionsoftParameterValidationException {
        final ContactActionQuery fq = new ContactActionQuery();

        fq.validateArguments();

        verify(fq).contactActionValidate();
    }
}
