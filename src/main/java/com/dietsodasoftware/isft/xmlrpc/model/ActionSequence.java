package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * http://help.infusionsoft.com/developers/tables/actionsequence
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/27/13
 * Time: 11:03 PM
 */
@TableName(table = "ActionSequence")
public class ActionSequence extends Model {

    public ActionSequence(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField<ActionSequence>> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField<ActionSequence> {
        Id(Integer.class, Access.Read),
        TemplateName(String.class, Access.Read),
        VisibleToTheseUsers(String.class, Access.Read)
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
