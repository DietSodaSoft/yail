package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/ProductInterest.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:54 PM
 */
@TableName(table = "ProductInterest")
public class ProductInterest extends Model {

    public ProductInterest(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class),
        ObjectId(Integer.class),
        ObjType(String.class),
        ProductId(Integer.class),
        ProductType(String.class),
        Qty(Integer.class),
        DiscountPercent(Integer.class)
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