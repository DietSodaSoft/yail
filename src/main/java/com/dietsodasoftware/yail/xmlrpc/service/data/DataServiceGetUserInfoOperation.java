package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.oauth2.client.annotations.RequiresOAuth;
import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.InfusionsoftUserInfo;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: wendel.schultz
 * Date: 4/18/14
 */
@RequiresOAuth
@InfusionsoftRpc(service = "DataService", method = "getUserInfo")
public class DataServiceGetUserInfoOperation extends InfusionsoftXmlRpcServiceOperation<InfusionsoftUserInfo> {

    @Override
    protected List<?> getOperationParameters() {
        return Collections.emptyList();
    }

    protected boolean includeApiKey(){
        return false;
    }

    @Override
    public InfusionsoftUserInfo parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        final Map<String, Object> modelMap = (Map<String, Object>) rawResponse;
        return new InfusionsoftUserInfo(modelMap);
    }
}
