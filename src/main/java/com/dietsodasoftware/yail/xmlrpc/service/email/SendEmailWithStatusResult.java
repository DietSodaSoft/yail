package com.dietsodasoftware.yail.xmlrpc.service.email;

import java.util.Map;

public class SendEmailWithStatusResult {
    private enum Fields {
        Successful,
        Email,
        Message

    }
    private final Map<String, String> rawResult;
    public SendEmailWithStatusResult(Map<String, String> rawResult){
        this.rawResult = rawResult;
    }

    public String getIsSuccesful(){
        return rawResult.get(Fields.Successful.name());
    }

    public String getEmail(){
        return rawResult.get(Fields.Email.name());
    }

    public String getMessage(){
        return rawResult.get(Fields.Message.name());
    }

}
