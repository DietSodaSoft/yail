package com.dietsodasoftware.yail.xmlrpc.service.authentication;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.DigestionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 4/18/13
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationServiceAuthenticateForTemporaryKey extends InfusionsoftXmlRpcServiceOperation<String> {

    private static final String RPC_NAME = "DataService.getTemporaryKey";

    private final String vendorKey;
    private final String username;
    private final String passwordHash;

    public AuthenticationServiceAuthenticateForTemporaryKey(String vendorKey, String username, String password) {
        this.vendorKey = vendorKey;
        this.username = username;
        this.passwordHash = DigestionUtils.getMD5ForString(password);
    }

    protected boolean includeApiKey(){
        return false;
    }

    @Override
    protected List<?> getOperationParameters() {

        final List<Object> params = new LinkedList<Object>();
        params.add(vendorKey);
        params.add(username);
        params.add(passwordHash);

        return params;

    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public String parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse == null || !(rawResponse instanceof String)){
            throw new InfusionsoftAuthorizationFailureException(("Unable to authenticate to get temporary API key. " + rawResponse)); //sadly, we never reach this because XmlRpcException is thrown
        }

        return (String)rawResponse;
    }
}
