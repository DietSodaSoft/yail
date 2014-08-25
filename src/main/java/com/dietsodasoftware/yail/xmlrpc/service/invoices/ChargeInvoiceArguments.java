package com.dietsodasoftware.yail.xmlrpc.service.invoices;

public class ChargeInvoiceArguments {
    private ChargeInvoiceArguments(){
    }

    int invoiceId;
    String notes;
    int creditCardId;
    int merchantAccountId;
    boolean bypassCommissions;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final ChargeInvoiceArguments args;
        private Builder(){
            args = new ChargeInvoiceArguments();
        }

        public ChargeInvoiceArguments build(){
            return args;
        }

        public Builder invoiceId(int invoiceId){
            args.invoiceId = invoiceId;
            return this;
        }

        public Builder creditCardId(int creditCardId){
            args.creditCardId = creditCardId;
            return this;
        }

        public Builder notes(String notes){
            args.notes = notes;
            return this;
        }

        public Builder bypassCommissions(boolean bypassCommissions){
            args.bypassCommissions = bypassCommissions;
            return this;
        }

        public Builder merchantAccountId(int merchantAccountId){
            args.merchantAccountId = merchantAccountId;
            return this;
        }

    }

}
