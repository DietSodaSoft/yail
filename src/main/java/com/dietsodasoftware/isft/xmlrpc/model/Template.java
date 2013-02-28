package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * http://help.infusionsoft.com/developers/tables/template
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/27/13
 * Time: 11:11 PM
 */
@TableName(table = "Template")
public class Template extends Model {

    public Template(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class),
        PieceType(String.class),
        PieceTitle(String.class),
        Categories(String.class)
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
