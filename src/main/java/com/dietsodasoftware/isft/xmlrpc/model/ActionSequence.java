package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class),
        TemplateName(String.class),
        VisibleToTheseUsers(String.class)
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
