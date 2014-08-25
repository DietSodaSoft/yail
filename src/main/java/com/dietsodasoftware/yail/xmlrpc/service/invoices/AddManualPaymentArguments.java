package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import java.util.Date;

public class AddManualPaymentArguments {
    private AddManualPaymentArguments(){
    }

    int invoiceId;
    double amt;
    Date paymentDate;
    String paymentType;
    String paymentDescription;
    boolean bypassCommissions;


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final AddManualPaymentArguments args;
        private Builder(){
            args = new AddManualPaymentArguments();
        }

        public AddManualPaymentArguments build(){
            return args;
        }

        public Builder invoiceId(int invoiceId){
            args.invoiceId = invoiceId;
            return this;
        }

        public Builder amt(double amt){
            args.amt = amt;
            return this;
        }

        public Builder paymentDate(Date paymentDate){
            args.paymentDate = paymentDate;
            return this;
        }

        public Builder paymentType(String paymentType){
            args.paymentType = paymentType;
            return this;
        }

        public Builder paymentDescription(String paymentDescription){
            args.paymentDescription = paymentDescription;
            return this;
        }

        public Builder bypassCommissions(boolean bypassCommissions){
            args.bypassCommissions = bypassCommissions;
            return this;
        }


    }

}
