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
        ContactId(Integer.class, Access.Read),
        BillName(String.class, Access.Read, Access.Update, Access.Add),
        FirstName(String.class, Access.Read, Access.Update, Access.Add),
        LastName(String.class, Access.Read, Access.Update, Access.Add),
        PhoneNumber(String.class, Access.Read, Access.Update, Access.Add),
        Email(String.class, Access.Read, Access.Update, Access.Add),
        BillAddress1(String.class, Access.Read, Access.Update, Access.Add),
        BillAddress2(String.class, Access.Read, Access.Update, Access.Add),
        BillCity(String.class, Access.Read, Access.Update, Access.Add),
        BillState(String.class, Access.Read, Access.Update, Access.Add),
        BillZip(String.class, Access.Read, Access.Update, Access.Add),
        BillCountry(String.class, Access.Read, Access.Update, Access.Add),
        ShipFirstName(String.class, Access.Read, Access.Update, Access.Add),
        ShipMiddleName(String.class, Access.Read, Access.Update, Access.Add),
        ShipLastName(String.class, Access.Read, Access.Update, Access.Add),
        ShipCompanyName(String.class, Access.Read, Access.Update, Access.Add),
        ShipPhoneNumber(String.class, Access.Read, Access.Update, Access.Add),
        ShipAddress1(String.class, Access.Read, Access.Update, Access.Add),
        ShipAddress2(String.class, Access.Read, Access.Update, Access.Add),
        ShipCity(String.class, Access.Read, Access.Update, Access.Add),
        ShipState(String.class, Access.Read, Access.Update, Access.Add),
        ShipZip(String.class, Access.Read, Access.Update, Access.Add),
        ShipCountry(String.class, Access.Read, Access.Update, Access.Add),
        ShipName(String.class, Access.Read, Access.Update, Access.Add),
        NameOnCard(String.class, Access.Read, Access.Update, Access.Add),

        CardNumber(String.class, Access.Add),
        Last4(String.class, Access.Read),

        ExpirationMonth(String.class, Access.Read, Access.Update, Access.Add),
        ExpirationYear(String.class, Access.Read, Access.Update, Access.Add),
        CVV2(String.class, Access.Add, Access.Update),
        Status(Integer.class, Access.Read),    // see CardStatus

        CardType(String.class, Access.Read, Access.Update, Access.Add),
        StartDateMonth(String.class, Access.Read, Access.Update, Access.Add),
        StartDateYear(String.class, Access.Read, Access.Update, Access.Add),
        MaestroIssueNumber(String.class, Access.Read, Access.Update, Access.Add)
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

    /* Status:
    0: Unknown
    1: Invalid
    2: Deleted
    3: Valid/Good
    4: Inactive
    */
    public enum CardStatus {
        Unknown(0), Invalid(1), Deleted(2), Valid(3), Inactive(4);

        private final int dbConstant;

        private CardStatus(int dbConstant) {
            this.dbConstant = dbConstant;
        }

        public int getDbConstant(){
            return dbConstant;
        }

        public static CardStatus fromDbConstant(int dbConstant){

            for(CardStatus status: CardStatus.values()){
                if(status.getDbConstant() == dbConstant){
                    return status;
                }
            }

            throw new IllegalArgumentException("Unknown Credit Card status constant: " + dbConstant);
        }
    }

    public static Builder<CreditCard> builder(){
        return new Builder<CreditCard>(CreditCard.class);
    }
}
