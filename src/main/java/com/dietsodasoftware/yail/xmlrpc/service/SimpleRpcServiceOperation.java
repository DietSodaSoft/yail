package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

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

    private final String rpcName;
    private final List<?> parameters;

    public SimpleRpcServiceOperation(String rpcName, Object... parameters) {
        this.rpcName = rpcName;
        this.parameters = ListFactory.quickUnmodifiableLinkedList(parameters);
    }

    @Override
    protected List<?> getOperationParameters() {
        return parameters;
    }

    @Override
    public String getRpcName() {
        return rpcName;
    }

    @Override
    public T parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        return (T)rawResponse;
    }
}
