package com.dietsodasoftware.yail.xmlrpc.service.invoices;

public class AddOrderItemArguments {
    private AddOrderItemArguments(){
    }

    int invoiceId;
    int productId;
    int type;
    double price;
    int quantity;
    String title;
    String description;


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final AddOrderItemArguments args;
        private Builder(){
            args = new AddOrderItemArguments();
        }

        public AddOrderItemArguments build(){
            return args;
        }

        public Builder invoiceId(int invoiceId){
            args.invoiceId = invoiceId;
            return this;
        }

        public Builder productId(int productId){
            args.productId = productId;
            return this;
        }

        public Builder type(int type){
            args.type = type;
            return this;
        }

        public Builder price(double price){
            args.price = price;
            return this;
        }

        public Builder quantity(int quantity){
            args.quantity = quantity;
            return this;
        }


        public Builder title(String title){
            args.title = title;
            return this;
        }

        public Builder description(String description){
            args.description = description;
            return this;
        }


    }

}
