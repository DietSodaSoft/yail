package com.dietsodasoftware.yail.xmlrpc.service.authentication;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.DigestionUtils;
import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/18/13
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */
@InfusionsoftRpc(service = "DataService", method = "getTemporaryKey")
public class AuthenticationServiceAuthenticateForTemporaryKey extends SimpleRpcServiceOperation<String> {

    public AuthenticationServiceAuthenticateForTemporaryKey(String vendorKey, String username, String password) {
        super(vendorKey, username, DigestionUtils.getMD5ForString(password));
    }

    protected boolean includeApiKey(){
        return false;
    }

    @Override
    public String parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse == null || !(rawResponse instanceof String)){
            throw new InfusionsoftAuthorizationFailureException(("Unable to authenticate to get temporary API key. " + rawResponse)); //sadly, we never reach this because XmlRpcException is thrown
        }

        return (String)rawResponse;
    }
}
