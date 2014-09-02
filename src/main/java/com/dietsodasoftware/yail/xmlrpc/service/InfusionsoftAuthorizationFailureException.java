package com.dietsodasoftware.yail.xmlrpc.service;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 3/1/13
 * Time: 3:05 PM
 */
public class InfusionsoftAuthorizationFailureException extends InfusionsoftXmlRpcException {

    public InfusionsoftAuthorizationFailureException(String s) {
        super(s);
    }

    public InfusionsoftAuthorizationFailureException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InfusionsoftAuthorizationFailureException(Throwable throwable) {
        super(throwable);
    }
}
