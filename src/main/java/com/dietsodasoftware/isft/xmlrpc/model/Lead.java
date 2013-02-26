package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
        Id(Integer.class),
        OpportunityTitle(String.class),
        ContactID(Integer.class),
        AffiliateId(Integer.class),
        UserID(Integer.class),
        StageID(Integer.class),
        StatusID(Integer.class),
        Leadsource(String.class),
        Objection(String.class),
        ProjectedRevenueLow(Double.class),
        ProjectedRevenueHigh(Double.class),
        OpportunityNotes(String.class),
        DateCreated(Date.class),
        LastUpdated(Date.class),
        LastUpdatedBy(Integer.class),
        CreatedBy(Integer.class),
        EstimatedCloseDate(Date.class),
        NextActionDate(Date.class),
        NextActionNotes(String.class)
        ;

        private final Class<?> fieldClass;

        private Field(Class<?> fieldClass) {
            this.fieldClass = fieldClass;
        }

        @Override
        public Class<?> typeClass() {
            return fieldClass;
        }
    }
}
