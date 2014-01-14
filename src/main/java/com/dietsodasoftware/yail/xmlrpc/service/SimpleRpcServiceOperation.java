package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.utils.InfusionsoftDateTimeService;
import com.dietsodasoftware.yail.xmlrpc.utils.InfusionsoftDateTimeService.DateTimeBinding;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * For easy operations which generally return "primitives": Integer, String, Double, etc.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:49 AM
 */
public class SimpleRpcServiceOperation<T> extends InfusionsoftXmlRpcServiceOperation<T> {

    private final List<Object> parameters;

    /**
     * For simple operations which aren't subclassed, this ctor initializes the list of
     * @param serviceName
     * @param methodName
     */
    public SimpleRpcServiceOperation(String serviceName, String methodName){
        super(serviceName, methodName);
        this.parameters = ListFactory.quickLinkedList();
    }

    public SimpleRpcServiceOperation(Object... parameters) {
        this.parameters = ListFactory.quickUnmodifiableLinkedList(parameters);
    }

    @Override
    protected List<Object> getOperationParameters() {
        return parameters;
    }

    @Override
    public T parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        return (T)rawResponse;
    }

    protected static String bindingValue(InfusionsoftDateTimeService dateService, DateTime dateTime, DateTimeBinding binding){
        final Date date = dateService.normalizeDateToInfusionsoftDate(dateTime);
        return dateService.dateAsServiceBindingValue(date, binding);
    }

    protected static String dateBindingValue(InfusionsoftDateTimeService dateService, DateTime dateTime){
        return bindingValue(dateService, dateTime, DateTimeBinding.Date);
    }

    protected static String dateAndTimeBindingValue(InfusionsoftDateTimeService dateService, DateTime dateTime){
        return bindingValue(dateService, dateTime, DateTimeBinding.DateTime);
    }
}
