package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/User.html
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:23 PM
 */
@TableName(table = "User")
public class User extends Model {

    public User(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        City(String.class, Access.Read),
        Email(String.class, Access.Read),
        EmailAddress2(String.class, Access.Read),
        EmailAddress3(String.class, Access.Read),
        FirstName(String.class, Access.Read),
        HTMLSignature(String.class, Access.Read),
        Id(Integer.class, Access.Read),
        LastName(String.class, Access.Read),
        MiddleName(String.class, Access.Read),
        Nickname(String.class, Access.Read),
        Phone1(String.class, Access.Read),
        Phone1Ext(String.class, Access.Read),
        Phone1Type(String.class, Access.Read),
        Phone2(String.class, Access.Read),
        Phone2Ext(String.class, Access.Read),
        Phone2Type(String.class, Access.Read),
        PostalCode(String.class, Access.Read),
        Signature(String.class, Access.Read),
        SpouseName(String.class, Access.Read),
        State(String.class, Access.Read),
        StreetAddress1(String.class, Access.Read),
        StreetAddress2(String.class, Access.Read),
        Suffix(String.class, Access.Read),
        Title(String.class, Access.Read),
        ZipFour1(String.class, Access.Read)
        ;


        private final Class<?> fieldClass;
        private final List<Access> fieldAccess;

        private Field(Class<?> fieldClass, Access... fieldAccess) {
            if(fieldAccess == null){ throw new RuntimeException("Invalid null fieldAccess argument"); }
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
