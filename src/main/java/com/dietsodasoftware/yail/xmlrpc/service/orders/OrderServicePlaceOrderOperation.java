package com.dietsodasoftware.yail.xmlrpc.service.orders;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

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
@InfusionsoftRpc(service = "OrderService", method = "placeOrder")
public class OrderServicePlaceOrderOperation extends InfusionsoftXmlRpcServiceOperation<OrderPlacementResult> {

    private final OrderPlacementArguments arguments;

    public OrderServicePlaceOrderOperation(OrderPlacementArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List<Object> args = new LinkedList<Object>();
        return ListFactory.quickUnmodifiableLinkedList(
                arguments.contactId ,
                arguments.creditCardId,
                arguments.payPlanId,
                arguments.productIds,
                arguments.subscriptionPlanIds,
                arguments.processSpecials,
                arguments.promoCodes,
                arguments.leadAffiliateId,
                arguments.affiliateId
        );
    }


    @Override
    public OrderPlacementResult parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        final Map<String, String> rawMap = (Map<String, String>) rawResponse;
        return new OrderPlacementResult(rawMap);
    }
}
