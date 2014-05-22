package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftParameterValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/24/13
 * Time: 2:48 AM
 */
public class DataServiceQueryOperationTest {
    private DataServiceQueryOperation<Contact> query;

    @Before
    public void before(){
        query = new DataServiceQueryOperation<Contact>(DataServiceQueryFilter.builder(Contact.class).build());
    }

    @Test(expected = InfusionsoftParameterValidationException.class)
    public void testAscendingRequiresOrderByField() throws InfusionsoftParameterValidationException {
        query.ascending().validateArguments();
    }

    @Test(expected = InfusionsoftParameterValidationException.class)
    public void testOrderByRequiresAscending() throws InfusionsoftParameterValidationException {
        query.orderByCustomField("field").validateArguments();
    }

    @Test
    public void testOrderByAndAscendingAreBothSet() throws InfusionsoftParameterValidationException {
        query.orderByCustomField("field").ascending().validateArguments();
    }

    @Test
    public void testOrderByAndAscendingAreNotSet() throws InfusionsoftParameterValidationException {
        query.validateArguments();
    }

}
