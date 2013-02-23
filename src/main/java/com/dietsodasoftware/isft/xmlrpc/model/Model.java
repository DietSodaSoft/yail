package com.dietsodasoftware.isft.xmlrpc.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public abstract class Model {
	
	private final Map<String, Object> values;
	public Model(Map<String, Object> model){
		values = Collections.unmodifiableMap(model);
	}
	
	public <T> T getFieldValue(NamedField field){
		return (T) values.get(field.name());
	}

    public final Map<String, Object> getStruct(){
        return values;
    }
	
	@Override
	public String toString(){
		return values.toString();
	}
	
	public abstract <T extends NamedField> Collection<T> allFields();

}
