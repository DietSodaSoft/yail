package com.dietsodasoftware.yail.xmlrpc.service.email;

import java.util.Map;

public class SendTemplateWithStatusResult {
    private enum Fields {
        Successful,
        ContactId,
        Message;

    }
    private final Map<String, String> rawResult;
    public SendTemplateWithStatusResult(Map<String, String> rawResult){
        this.rawResult = rawResult;
    }

    public String getSuccessStatus(){
        return rawResult.get(Fields.Successful.name());
    }

    public String getContactId(){
        return rawResult.get(Fields.ContactId.name());
    }

    public String getMessage(){
        return rawResult.get(Fields.Message.name());
    }

}
