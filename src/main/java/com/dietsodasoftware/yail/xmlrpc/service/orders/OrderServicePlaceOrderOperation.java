package com.dietsodasoftware.yail.xmlrpc.service.orders;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * http://help.infusionsoft.com/api-docs/orderservice
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/18/13
 * Time: 5:05 PM
 */
public class OrderServicePlaceOrderOperation extends InfusionsoftXmlRpcServiceOperation<OrderPlacementResult> {
    private static final String RPC_NAME = "OrderService.placeOrder";

    private final OrderPlacementArguments arguments;

    public OrderServicePlaceOrderOperation(OrderPlacementArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> args = new LinkedList<Object>();

        args.add(arguments.contactId);
        args.add(arguments.creditCardId);
        args.add(arguments.payPlanId);
        args.add(arguments.productIds);
        args.add(arguments.subscriptionPlanIds);
        args.add(arguments.processSpecials);
        args.add(arguments.promoCodes);
        args.add(arguments.leadAffiliateId);
        args.add(arguments.affiliateId);


        return args;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public OrderPlacementResult parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        final Map<String, String> rawMap = (Map<String, String>) rawResponse;
        return new OrderPlacementResult(rawMap);
    }
}
