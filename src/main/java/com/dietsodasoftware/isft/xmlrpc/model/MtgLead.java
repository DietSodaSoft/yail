package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    public enum Field implements NamedField<MtgLead> {
        Id(Integer.class, Access.Read),
        DateAppraisalOrdered(Date.class, Access.Read),
        DateAppraisalDone(Date.class, Access.Read),
        DateAppraisalReceived(Date.class, Access.Read),
        DateTitleOrdered(Date.class, Access.Read),
        DateTitleReceived(Date.class, Access.Read),
        DateRateLocked(Date.class, Access.Read),
        DateRateLockExpires(Date.class, Access.Read),
        CreditReportDate(Date.class, Access.Read),
        ApplicationDate(Date.class, Access.Read),
        ActualCloseDate(Date.class, Access.Read),
        FundingDate(Date.class, Access.Read)
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
