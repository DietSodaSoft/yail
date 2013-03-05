package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @link http://help.infusionsoft.com/developers/tables/ccharge
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/28/13
 * Time: 6:13 PM
 */
@TableName(table = "CCharge")
public class CreditCharge extends Model {

    public CreditCharge(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField<CreditCharge> {
        Id(Integer.class, Access.Read),
        CCId(Integer.class, Access.Read),
        PaymentId(String.class, Access.Read),
        MerchantId(String.class, Access.Read),
        OrderNum(String.class, Access.Read),
        RefNum(String.class, Access.Read),
        ApprCode(String.class, Access.Read),
        Amt(Double.class, Access.Read)
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
