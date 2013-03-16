package com.dietsodasoftware.isft.xmlrpc.service;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/24/13
 * Time: 4:52 PM
 */
public class InfusionsoftXmlRpcException extends Exception {
    private InfusionsoftXmlRpcException() {
    }

    public InfusionsoftXmlRpcException(String s) {
        super(s);
    }

    public InfusionsoftXmlRpcException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InfusionsoftXmlRpcException(Throwable throwable) {
        super(throwable);
    }
}
