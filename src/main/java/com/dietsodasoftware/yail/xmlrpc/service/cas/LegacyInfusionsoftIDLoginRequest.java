package com.dietsodasoftware.yail.xmlrpc.service.cas;

import com.dietsodasoftware.yail.xmlrpc.client.http.InfusionsoftHttpPostRequest;
import com.sun.istack.internal.NotNull;
import org.apache.http.client.fluent.Response;

import java.io.IOException;

/**
 * This is brand new but this is also on the deprecation path.  Infusionsoft isn't committed to this access paradigm and
 * it can change at any time.  Use at your own risk; DON'T USE!
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/13/13
 * Time: 8:17 PM
 */
@Deprecated
public class LegacyInfusionsoftIDLoginRequest extends InfusionsoftHttpPostRequest<CASLogin> {
    private static final String AUTHENTICATE_PATH = "app/rest/authenticateUser";

    public LegacyInfusionsoftIDLoginRequest(@NotNull UserTokens tokens) {
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
