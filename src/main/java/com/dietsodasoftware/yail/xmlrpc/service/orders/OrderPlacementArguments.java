package com.dietsodasoftware.yail.xmlrpc.service.orders;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/18/13
 * Time: 5:21 PM
 */
public class OrderPlacementArguments {
    private OrderPlacementArguments(){
        productIds = new LinkedList<Integer>();
        subscriptionPlanIds = new LinkedList<Integer>();
        promoCodes = new LinkedList<String>();
    }

    Integer contactId;
    Integer creditCardId;
    Integer payPlanId;
    final List<Integer> productIds;
    final List<Integer> subscriptionPlanIds;
    Boolean processSpecials;
    final List<String> promoCodes;

    int leadAffiliateId;
    int affiliateId;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final OrderPlacementArguments args;
        private Builder(){
            args = new OrderPlacementArguments();
        }

        public OrderPlacementArguments build(){
            verifyNotNull(args.contactId, "Contact ID");
            verifyNotNull(args.creditCardId, "Credit Card ID");
            verifyNotNull(args.payPlanId, "Pay Plan ID");
            verifyNotNull(args.processSpecials, "Process Specials");

            return args;
        }

        public Builder contactId(int contactId){
            args.contactId = contactId;
            return this;
        }

        public Builder creditCardId(int creditCardId){
            args.creditCardId = creditCardId;
            return this;
        }

        public Builder payPlanId(int payPlanId){
            args.payPlanId = payPlanId;
            return this;
        }

        public Builder addProductId(int productId){
            args.productIds.add(productId);
            return this;
        }

        public Builder addSubscriptionPlanId(int subscriptionPlanId){
            args.subscriptionPlanIds.add(subscriptionPlanId);
            return this;
        }

        public Builder processSpecials(boolean processSpecials){
            args.processSpecials = processSpecials;
            return this;
        }
        public Builder addPromoCode(String promoCode){
            args.promoCodes.add(promoCode);
            return this;
        }

        public Builder leadAffiliateId(int leadAffiliateId){
            args.leadAffiliateId = leadAffiliateId;
            return this;
        }

        public Builder affiliateId(int affiliateId){
            args.affiliateId = affiliateId;
            return this;
        }

        private void verifyNotNull(Object object, String name){
            if(object == null){
                throw new IllegalArgumentException("Argument " + name + " cannot be null");
            }
        }

    }
}
