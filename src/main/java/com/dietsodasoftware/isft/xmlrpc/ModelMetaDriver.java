package com.dietsodasoftware.isft.xmlrpc;

import com.dietsodasoftware.isft.xmlrpc.model.ActionSequence;
import com.dietsodasoftware.isft.xmlrpc.model.Affiliate;
import com.dietsodasoftware.isft.xmlrpc.model.Campaign;
import com.dietsodasoftware.isft.xmlrpc.model.CampaignStep;
import com.dietsodasoftware.isft.xmlrpc.model.Campaignee;
import com.dietsodasoftware.isft.xmlrpc.model.Company;
import com.dietsodasoftware.isft.xmlrpc.model.Contact;
import com.dietsodasoftware.isft.xmlrpc.model.ContactAction;
import com.dietsodasoftware.isft.xmlrpc.model.CreditCharge;
import com.dietsodasoftware.isft.xmlrpc.model.GroupAssign;
import com.dietsodasoftware.isft.xmlrpc.model.Lead;
import com.dietsodasoftware.isft.xmlrpc.model.Model;
import com.dietsodasoftware.isft.xmlrpc.model.MtgLead;
import com.dietsodasoftware.isft.xmlrpc.model.NamedField;
import com.dietsodasoftware.isft.xmlrpc.model.ProductInterest;
import com.dietsodasoftware.isft.xmlrpc.model.ProductInterestBundle;
import com.dietsodasoftware.isft.xmlrpc.model.Stage;
import com.dietsodasoftware.isft.xmlrpc.model.StageMove;
import com.dietsodasoftware.isft.xmlrpc.model.Status;
import com.dietsodasoftware.isft.xmlrpc.model.SubscriptionPlan;
import com.dietsodasoftware.isft.xmlrpc.model.Tag;
import com.dietsodasoftware.isft.xmlrpc.model.Template;
import com.dietsodasoftware.isft.xmlrpc.model.User;
import com.dietsodasoftware.isft.xmlrpc.model.UserGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/28/13
 * Time: 12:24 AM
 */
public class ModelMetaDriver {
    final static String spaces = "                                                                                 ";
    final static int indent = 4;

    public static void main( String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        final int longestFieldLength = determineLongestFieldNameLength();
        final int fieldColumnWidth = longestFieldLength + 3;

        System.out.println("Models: " + models.length);
        System.out.println(spaces.substring(0, indent + fieldColumnWidth) + "Read\tAdd\t\tUpdate\tDelete");

        for(Class model: models){
            final Model m = (Model) Model.getModelMapConstructor(model).newInstance(new HashMap());
            for(int i = 0; i < 32 + 4 + fieldColumnWidth; i++){
                System.out.print("-");
            }
            System.out.println("-");

            System.out.println( model.getSimpleName());
            for(NamedField f: m.allFields()){
                final int l = f.name().length();
                System.out.println(spaces.substring(0, indent) + f.name() + spaces.substring(0, (fieldColumnWidth - l)) +
                        displayValueFor(f.hasAccess(NamedField.Access.Read)) + "\t\t" +
                        displayValueFor(f.hasAccess(NamedField.Access.Add)) + "\t\t" +
                        displayValueFor(f.hasAccess(NamedField.Access.Update)) + "\t\t" +
                        displayValueFor(f.hasAccess(NamedField.Access.Delete))
                );
            }
        }
    }

    private static final String displayValueFor(boolean show){
        if(show){
            return "X";
        }
        return " ";
    }

    private static final int determineLongestFieldNameLength() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        int longest = 0;
        for(Class model: models){
            final Model m = (Model) Model.getModelMapConstructor(model).newInstance(new HashMap());
            for(NamedField f: m.allFields()){
                longest = Math.max(longest, f.name().length());
            }
        }

        return longest;
    }

    private static final Class[] models = new Class[]{
            ActionSequence.class,
            Affiliate.class,
            Campaign.class,
            Campaignee.class,
            CampaignStep.class,
            Company.class,
            Contact.class,
            ContactAction.class,
            CreditCharge.class,
            GroupAssign.class,
            Lead.class,
            MtgLead.class,
            ProductInterest.class,
            ProductInterestBundle.class,
            Stage.class,
            StageMove.class,
            Status.class,
            SubscriptionPlan.class,
            Tag.class,
            Template.class,
            User.class,
            UserGroup.class
    };
}
