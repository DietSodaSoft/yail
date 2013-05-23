package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.*;

/**
 * api_field_access.xml
 *
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 3/6/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@TableName(table = "RecurringOrder") // also JobRecurring
public class PaidSubscription extends Model {


    public PaidSubscription(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        ContactId(Integer.class, Access.Update, Access.Add, Access.Read),
        OriginatingOrderId(Integer.class, Access.Read),
        ProgramId(Integer.class, Access.Update, Access.Add, Access.Read),
        SubscriptionPlanId(Integer.class, Access.Update, Access.Add, Access.Read),
        ProductId(Integer.class, Access.Update, Access.Add, Access.Read),
        StartDate(Date.class, Access.Update, Access.Add, Access.Read),
        EndDate(Date.class, Access.Update, Access.Add, Access.Read),
        LastBillDate(Date.class, Access.Update, Access.Add, Access.Read),
        NextBillDate(Date.class, Access.Read),
        PaidThruDate(Date.class, Access.Update, Access.Add, Access.Read),  // BUG! Infusionsoft has aliased this with LastBillDate.  Use NextBillDate for what I think you want.
        BillingCycle(String.class, Access.Update, Access.Add, Access.Read),
        Frequency(Integer.class, Access.Update, Access.Add, Access.Read),
        BillingAmt(Double.class, Access.Update, Access.Add, Access.Read),
        Status(String.class, Access.Update, Access.Add, Access.Read),
        ReasonStopped(String.class, Access.Update, Access.Add, Access.Read),
        AutoCharge(Integer.class, Access.Update, Access.Add, Access.Read),
        CC1(Integer.class, Access.Update, Access.Add, Access.Read),
        CC2(Integer.class, Access.Update, Access.Add, Access.Read),
        NumDaysBetweenRetry(Integer.class, Access.Update, Access.Add, Access.Read),
        MaxRetry(Integer.class, Access.Update, Access.Add, Access.Read),
        MerchantAccountId(Integer.class, Access.Update, Access.Add, Access.Read),
        AffiliateId(Integer.class, Access.Update, Access.Add, Access.Read),
        PromoCode(String.class, Access.Update, Access.Add, Access.Read),
        LeadAffiliateId(Integer.class, Access.Update, Access.Add, Access.Read),
        Qty(Integer.class, Access.Update, Access.Add, Access.Read)
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

    public enum BillingCycleFrequency{
        Yearly(1),
        Monthly(2),
        Weekly(3),
        Daily(6)
        ;

        private BillingCycleFrequency(Integer code){
            this.code = code;
        }

        private final Integer code;
        public Integer getFrequencyCode(){
            return code;
        }

    }

    public enum Status{
        Active, Inactive;
    }

    public static Builder<PaidSubscription> builder(){
        return new Builder<PaidSubscription>(PaidSubscription.class);
    }
}
