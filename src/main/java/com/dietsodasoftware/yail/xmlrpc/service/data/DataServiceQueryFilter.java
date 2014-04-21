package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DataServiceQueryFilter<MT extends Model> {

    private final Map<String, Object> parameterValues;
    private final Class<MT> modelClass;

    private DataServiceQueryFilter(Class<MT> modelClass, final Map<String, Object> parameterValues){
        this.modelClass = modelClass;
        this.parameterValues = Collections.unmodifiableMap(parameterValues);
    }

    public static class Builder<MT> {
        private final Class<MT> modelClass;
        private Builder(Class<MT> modelClass){
            this.modelClass = modelClass;
        }

        public enum Like{
            before("%%%s"), after("%s%%"), surrounding("%%%s%%");

            private final String pattern;
            private Like(String pattern){
                this.pattern = pattern;
            }

            String getParameterValue(String compareTo){
                return String.format(pattern, compareTo);
            }
        }

        public enum Compare{
            gt("~>~"),
            lt("~<~"),
            gte("~>=~"),
            lte("~<=~")
            ;

            private final String compareString;
            private Compare(String compareString){
                this.compareString = compareString;
            }
        }

        private static final String NULL = "~null~";

        // required params
        private final Map<String, Object> parameterValues = new HashMap<String, Object>();

        private DataServiceQueryFilter.Builder fieldEquals(String fieldName, Object value){
            parameterValues.put(fieldName, value);
            return this;
        }

        private DataServiceQueryFilter.Builder fieldIsNull(String fieldName){
            parameterValues.put(fieldName, NULL);
            return this;
        }

        private DataServiceQueryFilter.Builder fieldLike(String fieldName, String value, Like like){
            parameterValues.put(fieldName, like.getParameterValue(value));
            return this;
        }

        public DataServiceQueryFilter.Builder fieldEquals(NamedField field, Object value){
            return fieldEquals(field.name(), value);
        }

        public DataServiceQueryFilter.Builder fieldIsNull(NamedField field){
            return fieldIsNull(field.name());
        }

        public DataServiceQueryFilter.Builder fieldLike(NamedField field, String value, Like like){
            return fieldLike(field.name(), value, like);
        }

        public DataServiceQueryFilter.Builder customFieldEquals(String fieldName, Object value){
            return fieldEquals("_" + Model.scrubCustomFieldName(fieldName), value);
        }

        public DataServiceQueryFilter.Builder customFieldIsNull(String fieldName){
            return fieldIsNull("_" + Model.scrubCustomFieldName(fieldName));
        }

        public DataServiceQueryFilter.Builder customFieldLike(String fieldName, String value, Like like){
            return fieldLike("_" + Model.scrubCustomFieldName(fieldName), value, like);
        }

        public DataServiceQueryFilter.Builder fieldCompare(NamedField field, Compare compare, String value){
            fieldEquals(field.name(), compare.compareString + value);
            return this;
        }

        public DataServiceQueryFilter.Builder customFieldCompare(String field, Compare compare, String value){
            fieldEquals("_" + Model.scrubCustomFieldName(field), compare.compareString + value);
            return this;
        }

        public <MT extends Model> DataServiceQueryFilter build(){
            return new DataServiceQueryFilter(modelClass, parameterValues);
        }
    }

    Map<String, Object> getFilterParameters(){
        return parameterValues;
    }

    Class<MT> getModelClass(){
        return modelClass;
    }

    public static <MT extends Model> Builder builder(Class<MT> modelClass){
        if(modelClass == null) { throw new IllegalArgumentException("must provide model class"); }

        return new Builder(modelClass);
    }

    public DataServiceQueryCountOperation<MT> createQueryCountOperation(){
        return new DataServiceQueryCountOperation<MT>(this);
    }

    public DataServiceQueryOperation<MT> createQueryOperation(){
        return new DataServiceQueryOperation<MT>(this);
    }
}
