package com.dietsodasoftware.isft.xmlrpc.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public abstract class Model {
	
	private final Map<String, Object> values;
	public Model(Map<String, Object> model){
		values = Collections.unmodifiableMap(model);
	}
	
	public Object getFieldValue(NamedField field){
		return values.get(field.name());
	}
	
	@Override
	public String toString(){
		return values.toString();
	}
	
	public abstract <T extends NamedField> Collection<T> allFields();

}
