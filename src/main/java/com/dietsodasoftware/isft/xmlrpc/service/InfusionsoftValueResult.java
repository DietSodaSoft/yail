package com.dietsodasoftware.isft.xmlrpc.service;

public class InfusionsoftValueResult<T> {
	
	private Object rawResult;
	
	public InfusionsoftValueResult(Object rawResult){
		this.rawResult = rawResult;
	}
	
	@SuppressWarnings("unchecked")
	public T getResult(){
		return (T)rawResult;
	}

}
