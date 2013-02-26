package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/ProductInterestBundle.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 9:05 PM
 */
@TableName(table = "ProductInterestBundle")
public class ProductInterestBundle extends Model {

    public ProductInterestBundle(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class),
        BundleName(String.class),
        Description(String.class)
        ;

        private final Class<?> fieldClass;

        private Field(Class<?> fieldClass) {
            this.fieldClass = fieldClass;
        }

        @Override
        public Class<?> typeClass() {
            return fieldClass;
        }
    }
}
