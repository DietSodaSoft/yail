package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

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
	
	public abstract <MT, T extends NamedField<MT>> Collection<T> allFields();

    public <MT extends Model, T extends NamedField<MT>> List<String> allFieldsNames(){
        final List<String> fieldNames = new LinkedList<String>();
        for(NamedField<?> field: this.allFields()){
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

    public static <T> String getTableNameForModel(Class<T> modelClass){
        if( modelClass.isAnnotationPresent(TableName.class)){
            final TableName tableAnnotation = modelClass.getAnnotation(TableName.class);
            return tableAnnotation.table();
        } else {
            throw new IllegalArgumentException("Must provide the return type Model class annotated by @TableName on class " + modelClass.getName());
        }
    }

}
