package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/Lead.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:42 PM
 */
@TableName(table = "Lead")
public class Lead extends Model {

    public Lead(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        OpportunityTitle(String.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        ContactID(Integer.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        AffiliateId(Integer.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        UserID(Integer.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        StageID(Integer.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        StatusID(Integer.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        Leadsource(String.class, Access.Add, Access.Read),
        Objection(String.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        ProjectedRevenueLow(Double.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        ProjectedRevenueHigh(Double.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        OpportunityNotes(String.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        DateCreated(Date.class, Access.Read, Access.Update, Access.Delete), // TODO: this seems like a bug
        LastUpdated(Date.class, Access.Read, Access.Update, Access.Delete), // TODO: this seems like a bug
        LastUpdatedBy(Integer.class, Access.Read, Access.Update, Access.Delete, Access.Add),  // TODO: this seems like a bug
        CreatedBy(Integer.class, Access.Read, Access.Update, Access.Delete, Access.Add),  // TODO: this seems like a bug
        EstimatedCloseDate(Date.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        NextActionDate(Date.class, Access.Read, Access.Update, Access.Delete, Access.Add),
        NextActionNotes(String.class, Access.Read, Access.Update, Access.Delete, Access.Add)
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
