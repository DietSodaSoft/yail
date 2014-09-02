package com.dietsodasoftware.yail.xmlrpc.service.email;

/**
 * User: wendel.schultz
 * Date: 1/13/14
 */
public enum EmailContentType {
    HTML("HTML"),
    Text("Text"),
    Multipart("Multipart");

    private final String rawValue;

    private EmailContentType(String rawValue) {
        this.rawValue = rawValue;
    }

    String getContentTypeParameter(){
        return rawValue;
    }
}
