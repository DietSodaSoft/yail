package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 7:15 PM
 */
@TableName(table = "Expense")
public class Expense extends Model {


    public Expense(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        ContactId(Integer.class, Access.Read),
        ExpenseType(String.class, Access.Read),
        TypeId(Integer.class, Access.Read),
        ExpenseAmt(Double.class, Access.Read),
        DateIncurred(Date.class, Access.Read),
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

    /**
     * Valid values for the ExpenseType field
     */
    public enum ExpenseType {
        CampaignStep, LeadSource, MarketingPiece;
    }

    public static Builder<Expense> builder(){
        return new Builder<Expense>(Expense.class);
    }
}
