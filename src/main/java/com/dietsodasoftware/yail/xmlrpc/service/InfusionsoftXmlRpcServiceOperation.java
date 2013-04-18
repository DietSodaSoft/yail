package com.dietsodasoftware.yail.xmlrpc.service;

import com.dietsodasoftware.yail.xmlrpc.client.YailClient;

import java.util.LinkedList;
import java.util.List;

public abstract class InfusionsoftXmlRpcServiceOperation<T> {

	@SuppressWarnings("unchecked")
	public final List<?> getXmlRpcParameters(YailClient isft) throws InfusionsoftXmlRpcException {
		@SuppressWarnings("rawtypes")
		final List parameters = new LinkedList();
        if(includeApiKey()){
            parameters.add(isft.getKey());
        }
		parameters.addAll(getOperationParameters());
		
		return parameters;
	}
	
	abstract protected List<?> getOperationParameters();

    protected boolean includeApiKey(){
        return true;
    }

	public abstract String getRpcName();

	public abstract T parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException;
}
