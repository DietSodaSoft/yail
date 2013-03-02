package com.dietsodasoftware.isft.xmlrpc.service.authentication;

import com.dietsodasoftware.isft.xmlrpc.client.IsftClient; // imported for JavaDoc. Funny.
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.isft.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import com.dietsodasoftware.isft.xmlrpc.utils.DigestionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Unfortunately, when this fails, all you'll get is an XmlRpcException from the {@link IsftClient}.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 3/1/13
 * Time: 2:39 PM
 */
public class AuthenticationServiceAuthenticateUser extends InfusionsoftXmlRpcServiceOperation<Integer> {
    private static final String RPC_NAME = "DataService.authenticateUser";

    private final String username;
    private final String passwordHash;

    public AuthenticationServiceAuthenticateUser(String username, String password) {
        this.username = username;
        this.passwordHash = DigestionUtils.getMD5ForString(password);
    }

    @Override
    protected List<?> getOperationParameters() {

        final List<Object> params = new LinkedList<Object>();
        params.add(username);
        params.add(passwordHash);

        return params;

    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    @Override
    public Integer parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse == null || !(rawResponse instanceof Integer)){
            throw new InfusionsoftAuthorizationFailureException(("Unable to authenticate. " + rawResponse)); //sadly, we never reach this because XmlRpcException is thrown
        }

        return (Integer)rawResponse;
    }
}
