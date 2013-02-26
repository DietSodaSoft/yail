package com.dietsodasoftware.isft.xmlrpc.model;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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

    public List<String> allFieldsNames(){
        final List<String> fieldNames = new LinkedList<String>();
        for(NamedField field: allFields()){
            fieldNames.add(field.name());
        }

        return fieldNames;
    }

    public static <T extends Model> Constructor<T> getModelMapConstructor(Class<T> modelClass){
            try {
                return modelClass.getConstructor(Map.class);
            } catch (SecurityException e) {
                throw new RuntimeException("Unable to create instance of " + modelClass + ": must provide single-argument constructor which takes a Map", e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Unable to create instance of " + modelClass + ": must provide single-argument constructor which takes a Map", e);
            }
    }

}
