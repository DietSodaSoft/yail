package com.dietsodasoftware.yail.xmlrpc.service.invoices;

public class AddOrderItemArguments {
    private AddOrderItemArguments(){
    }

    public enum OrderItemType {
        Shipping("Shipping", 1),
        Tax("Contact", 2),
        Service_Misc("Service & Misc", 3),
        Product("Product", 4),
        Upsell_Product("Upsell Product", 5),
        Finance_Charge("Finance Charge", 6),
        Special("Special", 7),
        Program("Program", 8),
        Subscription_Plan("Subscription Plan", 9),
        Special_Free_Trial_Days("Special: Free Trial Days", 10),
        Special_Order_Total("Special: Order Total", 11),
        Special_Product("Special: Product", 12),
        Special_Category("Special: Category", 13),
        Special_Shipping("Special: Shipping", 14);

        private final Integer orderItemTypeDbId;
        private final String orderItemTypeDbName;
        private OrderItemType(String orderItemTypeDbName, Integer orderItemTypeDbId){
            this.orderItemTypeDbName = orderItemTypeDbName;
            this.orderItemTypeDbId = orderItemTypeDbId;
        }

        public Integer getOrderItemTypeDbId(){
            return orderItemTypeDbId;
        }

        public String getOrderItemTypeDbName(){
            return orderItemTypeDbName;
        }
    }

    int invoiceId;
    int productId;
    OrderItemType type;
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

        public Builder type(OrderItemType type){
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
