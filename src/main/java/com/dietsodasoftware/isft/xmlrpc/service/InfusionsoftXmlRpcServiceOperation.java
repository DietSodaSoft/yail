package com.dietsodasoftware.isft.xmlrpc.service;

import com.dietsodasoftware.isft.xmlrpc.client.IsftClient;

import java.util.LinkedList;
import java.util.List;

public abstract class InfusionsoftXmlRpcServiceOperation<T> {

	@SuppressWarnings("unchecked")
	public final List<?> getXmlRpcParameters(IsftClient isft){
		@SuppressWarnings("rawtypes")
		final List parameters = new LinkedList();
		parameters.add(isft.getKey());
		parameters.addAll(getOperationParameters());
		
		return parameters;
	}
	
	abstract protected List<?> getOperationParameters();
	

	public abstract String getRpcName();

	public abstract T parseResult(Object rawResponse) throws InfusionsoftResponseParsingException;
}
