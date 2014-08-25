package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import java.util.Date;

public class CreateBlankOrderArguments {
    private CreateBlankOrderArguments(){
    }

    int invoiceId;
    String title;
    Date orderDate;
    int leadAffiliateId;
    int saleAffiliateId;


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final CreateBlankOrderArguments args;
        private Builder(){
            args = new CreateBlankOrderArguments();
        }

        public CreateBlankOrderArguments build(){
            return args;
        }

        public Builder contactId(int contactId){
            args.invoiceId = contactId;
            return this;
        }

        public Builder leadAffiliateId(int leadAffiliateId){
            args.leadAffiliateId = leadAffiliateId;
            return this;
        }

        public Builder description(String description){
            args.title = description;
            return this;
        }

        public Builder orderDate(Date orderDate){
            args.orderDate = orderDate;
            return this;
        }

        public Builder saleAffiliateId(int saleAffiliateId){
            args.saleAffiliateId = saleAffiliateId;
            return this;
        }

    }

}
