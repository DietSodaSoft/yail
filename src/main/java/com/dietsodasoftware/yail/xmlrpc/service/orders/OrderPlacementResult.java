package com.dietsodasoftware.yail.xmlrpc.service.orders;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/18/13
 * Time: 5:12 PM
 */
public class OrderPlacementResult {
    private enum Fields {
        Successful,
        Code,
        RefNum,
        Message,

        //Order and Invoice results
        OrderId,
        InvoiceId
        ;

    }
    private final Map<String, String> rawResult;
    public OrderPlacementResult(Map<String, String> rawResult){
        this.rawResult = rawResult;
    }

    public String getSuccessMessage(){
        return rawResult.get(Fields.Successful.name());
    }

    public String getChargeCode(){
        return rawResult.get(Fields.Code.name());
    }

    public String getChargeReferenceNumber(){
        return rawResult.get(Fields.RefNum.name());
    }

    public String getChargeMessage(){
        return rawResult.get(Fields.Message.name());
    }

    public Long getOrderId(){
        final String idString = rawResult.get(Fields.OrderId.name());
        return Long.parseLong(idString);
    }

    public Long getInvoiceId(){
        final String idString = rawResult.get(Fields.InvoiceId.name());
        return Long.parseLong(idString);
    }
}
