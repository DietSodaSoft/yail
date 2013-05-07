package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @link http://help.infusionsoft.com/developers/tables/subscriptionplan
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/27/13
 * Time: 11:15 PM
 */
@TableName(table = "SubscriptionPlan")
public class SubscriptionPlan extends Model {

    public SubscriptionPlan(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        ProductId(Integer.class, Access.Update, Access.Add, Access.Read),
        Cycle(String.class, Access.Update, Access.Add, Access.Read),
        Frequency(Integer.class, Access.Update, Access.Add, Access.Read),
        PreAuthorizeAmount(Double.class, Access.Update, Access.Add, Access.Read),
        Prorate(Double.class, Access.Update, Access.Add, Access.Read),
        Active(Double.class, Access.Update, Access.Add, Access.Read),
        PlanPrice(Double.class, Access.Update, Access.Add, Access.Read)
        ;

        private final Class<?> fieldClass;
        private final List<Access> fieldAccess;

        private Field(Class<?> fieldClass, Access... fieldAccess) {
            if(fieldAccess == null || fieldAccess.length == 0){ throw new RuntimeException("Invalid null fieldAccess argument"); }
            this.fieldClass = fieldClass;
            this.fieldAccess = Arrays.asList(fieldAccess);
        }

        @Override
        public Class<?> typeClass() {
            return fieldClass;
        }

        @Override
        public boolean hasAccess(Access access){
            return fieldAccess.contains(access);
        }

        @Override
        public Collection<Access> getAccess(){
            return Collections.unmodifiableList(fieldAccess);
        }
    }
}
