package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.*;

/**
 * @link http://http://help.infusionsoft.com/developers/tables/creditcard
 */
@TableName(table = "CreditCard")
public class CreditCard extends Model {

    public CreditCard(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        ContactId(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        FirstName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        LastName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        PhoneNumber(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Email(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillAddress1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillAddress2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillCity(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillState(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillZip(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillCountry(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipFirstName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipMiddleName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipLastName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipCompanyName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipPhoneNumber(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipAddress1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipAddress2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipCity(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipState(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipZip(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipCountry(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ShipName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        NameOnCard(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),

        CardNumber(String.class, Access.Add),
        Last4(String.class, Access.Read),

        ExpirationMonth(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ExpirationYear(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        CVV2(String.class, Access.Read, Access.Update),
        Status(Integer.class, Access.Read),
        /* Status:
        0: Unknown
        1: Invalid
        2: Deleted
        3: Valid/Good
        4: Inactive
        */
        CardType(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        StartDateMonth(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        StartDateYear(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        MaestroIssueNumber(String.class, Access.Read, Access.Update, Access.Add, Access.Delete)
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
