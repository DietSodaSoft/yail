package com.dietsodasoftware.yail.xmlrpc.service.authentication;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;
import com.dietsodasoftware.yail.xmlrpc.utils.DigestionUtils;

/**
 * Unfortunately, when this fails, all you'll get is an XmlRpcException from the {@link com.dietsodasoftware.yail.xmlrpc.client.YailClient}.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 3/1/13
 * Time: 2:39 PM
 */
@InfusionsoftRpc(service = "DataService", method = "authenticateUser")
public class AuthenticationServiceAuthenticateUser extends SimpleRpcServiceOperation<Integer> {

    private AuthenticationServiceAuthenticateUser(String username, String hashword) {
        super((Object)username, hashword);
    }

    @Override
    public Integer parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse == null || !(rawResponse instanceof Integer)){
            throw new InfusionsoftAuthorizationFailureException(("Unable to authenticate. " + rawResponse)); //sadly, we never reach this because XmlRpcException is thrown
        }

        return (Integer)rawResponse;
    }

    public static AuthenticationServiceAuthenticateUser usingPlainTextPassword(String username, String password){
        final String hashword = DigestionUtils.getMD5ForString(password);
        return usingHashedPassword(username, password);
    }

    public static AuthenticationServiceAuthenticateUser usingHashedPassword(String username, String password){
        return new AuthenticationServiceAuthenticateUser(username, password);
    }
}
