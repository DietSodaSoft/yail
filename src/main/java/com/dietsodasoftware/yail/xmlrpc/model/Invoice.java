package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.*;

/**
 * http://help.infusionsoft.com/developers/tables/invoice
 *
 * @author Victor M. Miller
 */
@TableName(table = "Invoice")
public class Invoice extends Model {

    public Invoice(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        ContactId(String.class, Access.Read),
        JobId(Integer.class, Access.Read),
        DateCreated(Date.class, Access.Read),
        InvoiceTotal(Date.class, Access.Read),
        TotalPaid(String.class, Access.Read),
        TotalDue(Integer.class, Access.Read),
        PayStatus(String.class, Access.Read),
        CreditStatus(Date.class, Access.Read),
        RefundStatus(Integer.class, Access.Read),
        PayPlanStatus(String.class, Access.Read),
        AffiliateId(Integer.class, Access.Read),
        LeadAffiliateId(String.class, Access.Read),
        PromoCode(String.class, Access.Read),
        InvoiceType(String.class, Access.Read),
        Description(String.class, Access.Read),
        ProductSold(String.class, Access.Read),
        Synced(String.class, Access.Read)
        ;

        private final Class<?> fieldClass;
        private final List<Access> fieldAccess;

        private Field(Class<?> fieldClass, Access... fieldAccess) {
            if(fieldAccess == null || fieldAccess.length == 0){ throw new RuntimeException("Invalid null fieldAccess argument"); }
            this.fieldClass = fieldClass;
            this.fieldAccess = Arrays.asList(fieldAccess);
        }

        public Class<?> typeClass() {
            return fieldClass;
        }

        public boolean hasAccess(Access access){
            return fieldAccess.contains(access);
        }

        public Collection<Access> getAccess(){
            return Collections.unmodifiableList(fieldAccess);
        }
    }

    public static Builder<Invoice> builder(){
        return new Builder<Invoice>(Invoice.class);
    }
}
