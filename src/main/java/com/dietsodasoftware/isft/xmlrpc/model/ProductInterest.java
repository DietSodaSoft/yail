package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
        Id(Integer.class, Access.Read),
        ObjectId(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ObjType(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ProductId(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ProductType(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Qty(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        DiscountPercent(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete)
        ;

        private final Class<?> fieldClass;
        private final List<Access> fieldAccess;

        private Field(Class<?> fieldClass, Access... fieldAccess) {
            if(fieldAccess == null){ throw new RuntimeException("Invalid null fieldAccess argument"); }
            this.fieldClass = fieldClass;
            this.fieldAccess = Arrays.asList(fieldAccess);
        }

        @Override
        public Class<?> typeClass() {
            return fieldClass;
        }

        @Override
        public boolean hasAccess(Access access){
            return fieldAccess.contains(access);
        }

        @Override
        public Collection<Access> getAccess(){
            return Collections.unmodifiableList(fieldAccess);
        }
    }
}
