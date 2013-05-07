package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.*;

/**
 * @link http://developers.infusionsoft.com/dbDocs/Product.html
 */
@TableName(table = "Product")
public class Product extends Model {

    public Product(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        ProductName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ProductPrice(Double.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Sku(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShortDescription(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Taxable(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        CountryTaxable(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        StateTaxable(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        CityTaxable(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Weight(Double.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        IsPackage(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        NeedsDigitalDelivery(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Description(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        HideInStore(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Status(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        TopHTML(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BottomHTML(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShippingTime(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        //LargeImage(Blob.class, Access.Read, Access.Update, Access.Add, Access.Delete) TODO?
        InventoryNotifiee(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        InventoryLimit(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Shippable(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete)
        ;

        private final Class<?> fieldClass;
        private final List<Access> fieldAccess;

        private Field(Class<?> fieldClass, Access... fieldAccess) {
            if(fieldAccess == null || fieldAccess.length == 0){ throw new RuntimeException("Invalid null fieldAccess argument"); }
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
