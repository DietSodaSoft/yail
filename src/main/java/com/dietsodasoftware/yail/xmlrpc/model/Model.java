package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;
import com.dietsodasoftware.yail.xmlrpc.utils.ModelUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Model {
    private final static String [] customFieldScrubCharacters = new String[]{
            " ", ",", "'"
    };

    private final Map<String, NamedField> complexTypes;
	private final Map<String, Object> values;
	public Model(Map<String, Object> model){
        complexTypes = getComplexTypes();

        if(complexTypes == null) throw new IllegalStateException("Must provide at least empty complex type mapping");

        final Map<String, Object> fieldValues = new HashMap<String, Object>();
        for(String name: model.keySet()){
            final Object value = model.get(name);
            fieldValues.put(name, unmarshallField(name, value));
        }
		values = Collections.unmodifiableMap(fieldValues);
	}

    // For simple types, simply return the value.
    private final <T> T unmarshallField(String name, Object value){
        final Object resolved;

        final NamedField complexField = complexTypes.get(name);
        if(complexField == null){
            resolved = value;
        } else {
            resolved = new Model.Builder(complexField.typeClass(), (Map<String,Object>) value).build();
        }

        return (T) resolved;
    }

    // override this if the object has nested complex types
    protected Map<String, NamedField> getComplexTypes(){
        return Collections.emptyMap();
    }

	public <T> T getFieldValue(NamedField field){
		return (T) values.get(field.name());
	}

    public <T> T getCustomFieldValue(String fieldName){
        return (T) values.get("_" + fieldName);
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
            if(field.hasAccess(NamedField.Access.Read)){
                fieldNames.add(field.name());
            }
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


    public static class Builder<M extends Model>{
        private final Map<String, Object> values = new HashMap<String, Object>();
        private final Class<M> modelClass;

        public Builder(M prototype){
            this((Class<M>) prototype.getClass(), prototype.getStruct());
        }

        public Builder(Class<M> clazz, Map<String, Object> values){
            this(clazz);

            values.putAll(values);
        }

        public Builder(Class<M> modelClass){
            this.modelClass = modelClass;
        }

        public <T> Builder<M> setFieldValue(NamedField field, T value){
            values.put(field.name(), value);
            return this;
        }

        public <T> Builder<M> setCustomFieldValue(String customFieldName, T value) {
            values.put("_" + customFieldName, value);
            return this;
        }

        public M build(){
            return ModelUtils.newInstance(modelClass, values);
        }
    }

    public static String scrubCustomFieldName(String customField){
        if(customField == null){
            return null;
        }

        for(String scrub: customFieldScrubCharacters){
            customField = customField.replaceAll(scrub, "");
        }
        return customField;
    }



}
