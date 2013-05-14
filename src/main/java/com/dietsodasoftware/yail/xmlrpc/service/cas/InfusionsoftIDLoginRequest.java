package com.dietsodasoftware.yail.xmlrpc.service.cas;

import com.dietsodasoftware.yail.xmlrpc.client.http.InfusionsoftHttpPostRequest;
import com.sun.istack.internal.NotNull;
import org.apache.http.client.fluent.Response;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/13/13
 * Time: 8:17 PM
 */
public class InfusionsoftIDLoginRequest extends InfusionsoftHttpPostRequest<CASLogin> {
    private static final String AUTHENTICATE_PATH = "app/rest/authenticateUser";

    public InfusionsoftIDLoginRequest(@NotNull UserTokens tokens) {
        super(AUTHENTICATE_PATH);

        if(tokens == null){
            throw new IllegalArgumentException("Cannot authenticate using CAS without user tokens");
        }

        addEntityParameter("username", tokens.getUsername());
        addEntityParameter("password", tokens.getPassword());
    }


    @Override
    public CASLogin parseResponse(Response httpResponse) throws IOException {
        return parseResponseAsJsonObject(CASLogin.class, httpResponse);
    }
}
