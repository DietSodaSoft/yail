package com.dietsodasoftware.yail.xmlrpc.service.funnel;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created by wendel.schultz on 2/1/16.
 */
@InfusionsoftRpc(service = "FunnelService", method = "achieveGoal")
public class ApiServiceAchieveGoalOperation<T> extends SimpleRpcServiceOperation<T> {

    public ApiServiceAchieveGoalOperation(String integrationName, String goalName, int contactId){
        super(integrationName, goalName, contactId);
    }
}
