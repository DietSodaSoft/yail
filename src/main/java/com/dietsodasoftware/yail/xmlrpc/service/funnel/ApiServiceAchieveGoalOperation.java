package com.dietsodasoftware.yail.xmlrpc.service.funnel;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftAuthorizationFailureException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;

/**
 * Created by wendel.schultz on 2/1/16.
 *
 * SubscriptionPlan
 NumberOfCycles

 EmailAddStatus
 Id
 Email
 DateCreated
 Type
 LastSentDate
 LastOpenDate
 LastClickDate

 Here is the list of fields coming in a later release.

 CampaignStep
 DateInStage
 IncludeInForecast
 OrderRevenue
 MonthlyRevenue

 ProductInterest
 SubscriptionPlanId

 Product
 DateCreated
 LastUpdated

 CCharge
 PaymentGatewayId

 */
@InfusionsoftRpc(service = "FunnelService", method = "achieveGoal")
public class ApiServiceAchieveGoalOperation extends SimpleRpcServiceOperation<AchieveGoalResponse> {

    public ApiServiceAchieveGoalOperation(String integrationName, String goalName, int contactId){
        super(integrationName, goalName, contactId);
    }

    @Override
    public AchieveGoalResponse parseResult(Object rawResponse) throws InfusionsoftResponseParsingException, InfusionsoftAuthorizationFailureException {
        if(rawResponse != null){
            return new AchieveGoalResponse((Object[]) rawResponse);
        } else {
            return null;
        }
    }
}
