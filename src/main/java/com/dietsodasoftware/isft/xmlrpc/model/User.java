package com.dietsodasoftware.isft.xmlrpc.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:23 PM
 */
public class User extends Model {

    public User(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        City(String.class),
        Email(String.class),
        EmailAddress2(String.class),
        EmailAddress3(String.class),
        FirstName(String.class),
        HTMLSignature(String.class),
        Id(Integer.class),
        LastName(String.class),
        MiddleName(String.class),
        Nickname(String.class),
        Phone1(String.class),
        Phone1Ext(String.class),
        Phone1Type(String.class),
        Phone2(String.class),
        Phone2Ext(String.class),
        Phone2Type(String.class),
        PostalCode(String.class),
        Signature(String.class),
        SpouseName(String.class),
        State(String.class),
        StreetAddress1(String.class),
        StreetAddress2(String.class),
        Suffix(String.class),
        Title(String.class),
        ZipFour1(String.class)
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
