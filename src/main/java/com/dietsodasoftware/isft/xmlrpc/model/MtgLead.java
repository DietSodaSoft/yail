package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/MtgLead.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:40 PM
 */
@TableName(table = "MtgLead")
public class MtgLead extends Model {

    public MtgLead(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class),
        DateAppraisalOrdered(Date.class),
        DateAppraisalDone(Date.class),
        DateAppraisalReceived(Date.class),
        DateTitleOrdered(Date.class),
        DateTitleReceived(Date.class),
        DateRateLocked(Date.class),
        DateRateLockExpires(Date.class),
        CreditReportDate(Date.class),
        ApplicationDate(Date.class),
        ActualCloseDate(Date.class),
        FundingDate(Date.class)
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
