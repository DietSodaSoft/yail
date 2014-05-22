package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @link http://help.infusionsoft.com/developers/tables/affiliate
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/28/13
 * Time: 12:35 AM
 */
@TableName(table = "Affiliate")
public class Affiliate extends Model {

    public Affiliate(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }


    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        ContactId(Integer.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        ParentId(Integer.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        LeadAmt(Double.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        LeadPercent(Double.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        SaleAmt(Double.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        SalePercent(Double.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        PayoutType(Integer.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        DefCommissionType(Integer.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        Status(Integer.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        AffName(String.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        Password(String.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        AffCode(String.class, Access.Update, Access.Delete, Access.Add, Access.Read),
        NotifyLead(Integer.class, Access.Update, Access.Delete, Access.Add, Access.Read)
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

    public static Builder<Affiliate> builder(){
        return new Builder<Affiliate>(Affiliate.class);
    }
}
