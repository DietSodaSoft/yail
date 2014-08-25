package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/20/13
 * Time: 11:33 PM
 */
@InfusionsoftRpc(service = "DataService", method = "getAppSetting")
public class DataServiceGetAppSettingOperation extends SimpleRpcServiceOperation<String> {

    public DataServiceGetAppSettingOperation(String module, String key) {
        super(new String[]{module, key});
    }

}
