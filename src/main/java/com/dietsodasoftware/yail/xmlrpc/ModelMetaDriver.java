package com.dietsodasoftware.yail.xmlrpc;

import com.dietsodasoftware.yail.xmlrpc.model.ActionSequence;
import com.dietsodasoftware.yail.xmlrpc.model.Affiliate;
import com.dietsodasoftware.yail.xmlrpc.model.FollowUpSequence;
import com.dietsodasoftware.yail.xmlrpc.model.FollowUpSequenceStep;
import com.dietsodasoftware.yail.xmlrpc.model.FollowUpSequenceTarget;
import com.dietsodasoftware.yail.xmlrpc.model.Company;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.model.ContactAction;
import com.dietsodasoftware.yail.xmlrpc.model.CreditCharge;
import com.dietsodasoftware.yail.xmlrpc.model.GroupAssign;
import com.dietsodasoftware.yail.xmlrpc.model.Lead;
import com.dietsodasoftware.yail.xmlrpc.model.Model;
import com.dietsodasoftware.yail.xmlrpc.model.MtgLead;
import com.dietsodasoftware.yail.xmlrpc.model.NamedField;
import com.dietsodasoftware.yail.xmlrpc.model.Order;
import com.dietsodasoftware.yail.xmlrpc.model.PaidSubscription;
import com.dietsodasoftware.yail.xmlrpc.model.ProductInterest;
import com.dietsodasoftware.yail.xmlrpc.model.ProductInterestBundle;
import com.dietsodasoftware.yail.xmlrpc.model.Stage;
import com.dietsodasoftware.yail.xmlrpc.model.StageMove;
import com.dietsodasoftware.yail.xmlrpc.model.Status;
import com.dietsodasoftware.yail.xmlrpc.model.SubscriptionPlan;
import com.dietsodasoftware.yail.xmlrpc.model.Tag;
import com.dietsodasoftware.yail.xmlrpc.model.TagAssignment;
import com.dietsodasoftware.yail.xmlrpc.model.TagCategory;
import com.dietsodasoftware.yail.xmlrpc.model.Template;
import com.dietsodasoftware.yail.xmlrpc.model.User;
import com.dietsodasoftware.yail.xmlrpc.model.UserGroup;

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

            System.out.println(model.getSimpleName() + " : " + Model.getTableNameForModel(model));
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
            FollowUpSequence.class,
            FollowUpSequenceTarget.class,
            FollowUpSequenceStep.class,
            Company.class,
            Contact.class,
            ContactAction.class,
            CreditCharge.class,
            GroupAssign.class,
            Lead.class,
            MtgLead.class,
            Order.class,
            PaidSubscription.class,
            ProductInterest.class,
            ProductInterestBundle.class,
            Stage.class,
            StageMove.class,
            Status.class,
            SubscriptionPlan.class,
            Tag.class,
            TagAssignment.class,
            TagCategory.class,
            Template.class,
            User.class,
            UserGroup.class
    };
}
