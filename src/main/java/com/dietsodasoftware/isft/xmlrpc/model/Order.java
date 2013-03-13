package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * http://help.infusionsoft.com/developers/tables/job
 *
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 3/12/13
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
@TableName(table = "Job")
public class Order extends Model {


    public Order(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        JobTitle(String.class, Access.Update, Access.Add, Access.Read),
        ContactId(Integer.class, Access.Update, Access.Add, Access.Read),
        StartDate(Date.class, Access.Update, Access.Add, Access.Read),
        DueDate(Date.class, Access.Update, Access.Add, Access.Read),
        JobNotes(String.class, Access.Update, Access.Add, Access.Read),
        ProductId(Integer.class, Access.Update, Access.Add, Access.Read),
        JobStatus(String.class, Access.Update, Access.Add, Access.Read),
        DateCreated(Date.class, Access.Read),
        JobRecurringId(Integer.class, Access.Read),
        OrderType(String.class, Access.Update, Access.Add, Access.Read),
        OrderStatus(Integer.class, Access.Update, Access.Add, Access.Read),
        ShipFirstName(String.class, Access.Update, Access.Add, Access.Read),
        ShipMiddleName(String.class, Access.Update, Access.Add, Access.Read),
        ShipLastName(String.class, Access.Update, Access.Add, Access.Read),
        ShipCompany(String.class, Access.Update, Access.Add, Access.Read),
        ShipPhone(String.class, Access.Update, Access.Add, Access.Read),
        ShipStreet1(String.class, Access.Update, Access.Add, Access.Read),
        ShipStreet2(String.class, Access.Update, Access.Add, Access.Read),
        ShipCity(String.class, Access.Update, Access.Add, Access.Read),
        ShipState(String.class, Access.Update, Access.Add, Access.Read),
        ShipZip(String.class, Access.Update, Access.Add, Access.Read),
        ShipCountry(String.class, Access.Update, Access.Add, Access.Read)
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
