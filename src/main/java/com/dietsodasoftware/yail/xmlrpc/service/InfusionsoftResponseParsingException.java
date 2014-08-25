package com.dietsodasoftware.yail.xmlrpc.service;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/24/13
 * Time: 4:47 PM
 */
public class InfusionsoftResponseParsingException extends Exception {
    public InfusionsoftResponseParsingException() {
    }

    public InfusionsoftResponseParsingException(String s) {
        super(s);
    }

    public InfusionsoftResponseParsingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InfusionsoftResponseParsingException(Throwable throwable) {
        super(throwable);
    }
}
