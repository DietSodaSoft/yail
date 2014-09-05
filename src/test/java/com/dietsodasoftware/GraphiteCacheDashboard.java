package com.dietsodasoftware;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: wendel.schultz
 * Date: 5/7/14
 */
public class GraphiteCacheDashboard {
    final String NEWLINE = "\n";
    final String INDENT = "    ";
    final String COMMA = ",";

    final String INDENTx2 = INDENT + INDENT;
    final String INDENTx3 = INDENT + INDENTx2;
    final String INDENTx4 = INDENTx2 + INDENTx2;

    public static void main(String[] args){
        new GraphiteCacheDashboard().dash();
    }


    public void dash(){

        final StringBuilder dashboard = new StringBuilder();

        dashboard.append("[").append(NEWLINE);

        boolean first = true;
        for(String cache: cacheNames){

            if(first){
                first = false;
            } else {
                dashboard.append(COMMA)
                         .append(NEWLINE);
            }

            final String cacheGraphs = createGraphsForCache(cache);

            dashboard.append(cacheGraphs);

        }

        dashboard.append(NEWLINE)
                 .append("]")
                 .append(NEWLINE);

        System.out.println(dashboard.toString());

    }

    private String createGraphsForCache(String cache){
        final StringBuilder graphs = new StringBuilder();

        boolean first = true;
        for(String template: METRIC_TEMPLATES.keySet()){
            final String titleTemplate = METRIC_TEMPLATES.get(template);
            final String title = String.format(titleTemplate, cache);
            if(first){
                first = false;
            } else {
                graphs.append(COMMA)
                      .append(NEWLINE);
            }

            // the graph here
            graphs.append(createGraphForCache(cache, template, title));

        }

        return graphs.toString();
    }

    private String createGraphForCache(String cache, String metricTemplate, String theTitle){
        final StringBuilder graph = new StringBuilder();

        final String title = String.format(TEMPLATE_TITLE, theTitle);
        final String vTitle = String.format(TEMPLATE_VTITLE, ROLE_CAMP);
        final String vTitleRight = String.format(TEMPLATE_VTITLE_RIGHT, ROLE_FRONT);

        final String leftMetricName = String.format(metricTemplate, ROLE_CAMP, cache);
        final String rightMetricName = String.format(metricTemplate, ROLE_FRONT, cache);
        final String leftMetricAlias = String.format(TEMPLATE_METRIC_ALIAS, leftMetricName);

        final String rightMetricAxis = String.format(TEMPLATE_RIGHT_AXIS, rightMetricName);
        final String rightMetricAlias = String.format(TEMPLATE_METRIC_ALIAS, rightMetricAxis);


        final String leftMetricValue = String.format(TEMPLATE_METRIC_NAME, leftMetricAlias);
        final String rightMetricValue = String.format(TEMPLATE_METRIC_NAME, rightMetricAlias);

        graph.append(INDENTx2)
             .append("{")
             .append(NEWLINE);

        graph.append(INDENTx3)
             .append(title).append(COMMA).append(NEWLINE);
        graph.append(INDENTx3)
             .append(vTitle).append(COMMA).append(NEWLINE);
        graph.append(INDENTx3)
             .append(vTitleRight).append(COMMA).append(NEWLINE);

        graph.append(INDENT).append(INDENT).append(INDENT)
             .append(ATTRIBUTE_TARGET).append("[").append(NEWLINE);


        graph.append(INDENTx4)
             .append(leftMetricValue)
             .append(COMMA).append(NEWLINE);
        graph.append(INDENTx4)
             .append(rightMetricValue)
             .append(NEWLINE);

        graph.append(INDENTx3)
             .append("]").append(NEWLINE);

        graph.append(INDENTx2)
             .append("}");


        return graph.toString();
    }

    private static final String ATTRIBUTE_TARGET = "\"target\": ";
    private static final String TEMPLATE_TITLE = "\"title\": \"%s\"";
    private static final String TEMPLATE_VTITLE = "\"vtitle\": \"%s\"";
    private static final String TEMPLATE_VTITLE_RIGHT = "\"vtitleRight\": \"%s\"";
    private static final String TEMPLATE_METRIC_NAME = "\"%s\"";
    private static final String TEMPLATE_RIGHT_AXIS = "secondYAxis(%s)";

    private static final String TEMPLATE_METRIC_ALIAS = "aliasByNode(%s, 2, 3, 5)";

    // templates take the role and the cache name
    private static final String TEMPLATE_CACHE_READS = "production.InfusionsoftCRM.*.*.AllInfusion.%s.Cache.%s.reads:sum";
    private static final String TEMPLATE_CACHE_WRITES = "production.InfusionsoftCRM.*.*.AllInfusion.%s.Cache.%s.writes:sum";
    private static final String TEMPLATE_CACHE_OBJECTS = "production.InfusionsoftCRM.*.*.AllInfusion.%s.Cache.%s.objects:sum";
    private static final String TEMPLATE_CACHE_TENANTS = "production.InfusionsoftCRM.*.*.AllInfusion.%s.Cache.%s.reads:obvs";

    private static final Map<String, String> METRIC_TEMPLATES = new LinkedHashMap<String, String>();
    {
        METRIC_TEMPLATES.put(TEMPLATE_CACHE_READS, "%s Reads");
        METRIC_TEMPLATES.put(TEMPLATE_CACHE_WRITES, "%s Writes");
        METRIC_TEMPLATES.put(TEMPLATE_CACHE_OBJECTS, "%s Objects");
        METRIC_TEMPLATES.put(TEMPLATE_CACHE_TENANTS, "%s Tenants");
    }

    private static final String ROLE_CAMP = "CAMP";
    private static final String ROLE_FRONT = "FRONT";
    private static final String [] ROLES = new String[] {ROLE_CAMP, ROLE_FRONT};

    private static final String [] cacheNames = new String[] {
             "AffiliateCommissionProgramItems"
            ,"AffiliateCommissionPrograms"
            ,"AffiliateCommissions"
            ,"AffiliatePayouts"
            ,"AffiliateProgramAffiliates"
            ,"AffiliatePrograms"
            ,"AuthoringSnipptetDefaultHTML"
            ,"AutocompleteStrategyOptions"
            ,"Broadcasts"
            ,"CallProviders"
            ,"CallToAction"
            ,"CallsToAction"
            ,"Campaign"
            ,"CampaignCallToAction"
            ,"CampaignCallsToAction"
            ,"CampaignMasterLinks"
            ,"CampaignMilestone"
            ,"CampaignMilestones"
            ,"CampaignServiceCampaigns"
            ,"CampaignTaggedContacts"
            ,"ConfigAttributeAnnotationScannerMethodConfiguration"
            ,"ContactGroupCategories"
            ,"ContactGroupName"
            ,"ContactGroups"
            ,"CustomFormXids"
            ,"CustomForms"
            ,"CustomWebFormLinks"
            ,"DataFormDrillDownOptions"
            ,"DataServiceHandlerLocator"
            ,"DigitalProductFiles"
            ,"Discounts"
            ,"EmailAddStatus"
            ,"EmailAddressStatusCache"
            ,"EmailAutocompleteStrategyOptions"
            ,"FullfillmentProductReports"
            ,"GadgetsRendered"
            ,"LeadServiceStages"
            ,"LeadSourceNames"
            ,"LeadSources"
            ,"LinkFavorites"
            ,"MailAccounts"
            ,"MailBatchWaitTimes"
            ,"MailBatchs"
            ,"MailFolders"
            ,"MailMessages"
            ,"MailRemoteFolders"
            ,"MasterLinks"
            ,"Milestone"
            ,"Milestones"
            ,"NumPeopleWithTag"
            ,"PermissionsUserSettings"
            ,"ProductCategories"
            ,"ProductSubcategories"
            ,"PurchaseTriggers"
            ,"QuickBooksProducts"
            ,"ShoppingCartPayplans"
            ,"ShoppingCartSpecials"
            ,"ShoppingCartUpsells"
            ,"StageMoveTriggers"
            ,"TicketCategories"
            ,"TicketReleases"
            ,"TicketStageCategories"
            ,"TicketStages"
            ,"TicketTypeCategories"
            ,"TicketTypes"
            ,"TimesheetEntries"
            ,"TimesheetItems"
            ,"UserDefinedMessageSourceLabels"
            ,"WebFormCheckboxes"
            ,"WebFormCustomFields"
            ,"WorkflowInstances"
            ,"WorkflowItemIndex"
            ,"WorkflowItems"
            ,"Workflows"
            ,"actionCache"
            ,"actionIdCache"
            ,"actionSequenceCache"
            ,"affiliateCache"
            ,"affiliateCodeToIdCache"
            ,"campaignCategoryCache"
            ,"dbThemeDefinitions"
            ,"editorsInUseCache"
            ,"flowItemCache"
            ,"followUpSequenceCache"
            ,"formCache"
            ,"freeTrailDaysSpecialCache"
            ,"funnelLinkCache"
            ,"funnelMergeFieldCache"
            ,"funnelRulesCache"
            ,"groupMapCache"
            ,"lastPageViewCache"
            ,"leadSourceIdNameAllCache"
            ,"leadSourceIdNameCache"
            ,"leadSourceMediumCache"
            ,"leadSourceMessageCache"
            ,"leadSourceNameNameCache"
            ,"leadSourceVendorCache"
            ,"mailSentCache"
            ,"mergeContextCache"
            ,"mergeDefaultsCache"
            ,"mergeFieldCache"
            ,"mergeFieldSetCache"
            ,"optLinkCache"
            ,"orderTotalSpecialCache"
            ,"outcomeCache"
            ,"pieceCache"
            ,"productCategorySpecialCache"
            ,"productIdNameListCache"
            ,"productListCache"
            ,"productTotalSpecialCache"
            ,"programIdNameListCache"
            ,"programListCache"
            ,"publishedHtmlCache"
            ,"roundRobinCache"
            ,"rulesCache"
            ,"shippingTotalSpecialCache"
            ,"stepCache"
            ,"stepsCache"
            ,"subUserCache"
            ,"systemEmailCache"
            ,"templateCache"
            ,"trackableLinkCache"
            ,"trustedKeyCache"
            ,"userCache"
            ,"visitorCache"
            ,"visitorHashCache"

    };

}



