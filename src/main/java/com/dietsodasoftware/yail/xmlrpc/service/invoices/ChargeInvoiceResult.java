package com.dietsodasoftware.yail.xmlrpc.service.invoices;

import java.util.Map;

/**
 * https://developer.infusionsoft.com/docs/read/Invoice_Service#chargeInvoice
 */
public class ChargeInvoiceResult {
    private enum Fields {
        Successful,
        Code,
        RefNum,
        Message

    }
    private final Map<String, String> rawResult;
    public ChargeInvoiceResult(Map<String, String> rawResult){
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

}
