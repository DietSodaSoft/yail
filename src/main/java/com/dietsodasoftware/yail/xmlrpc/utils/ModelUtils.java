package com.dietsodasoftware.yail.xmlrpc.utils;

import com.dietsodasoftware.yail.xmlrpc.model.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;

/**
 * User: wendel.schultz
 * Date: 1/14/14
 */
public class ModelUtils {
    private ModelUtils(){}

    public static <T extends Model> T newInstance(Class<T> modelClass, Map<String, Object> modelData){
        final Constructor<T> ctor = Model.getModelMapConstructor(modelClass);

        return newInstance(ctor, modelData);
    }

    public static <T extends Model> T newInstance(Constructor<T> ctor, Map<String, Object> modelData){
        final String modelClass = ctor.getName();
        try {
            return ctor.newInstance(Collections.unmodifiableMap(modelData));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to create instance of " + modelClass + ".", e);
        }

    }

}
