package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/Company.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 7:20 PM
 */
@TableName(table = "Company")
public class Company extends Model {

    public Company(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }


    public enum Field implements NamedField {
        Address1Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Address2Street1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Address2Street2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Address2Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Address3Street1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Address3Street2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Address3Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Anniversary(Date.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        AssistantName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        AssistantPhone(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        BillingInformation(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Birthday(Date.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        City(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        City2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        City3(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Company(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        AccountId(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        CompanyID(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ContactNotes(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ContactType(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Country(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Country2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Country3(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        CreatedBy(Integer.class, Access.Read),
        DateCreated(Date.class, Access.Read),
        Email(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        EmailAddress2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        EmailAddress3(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Fax1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Fax1Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Fax2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Fax2Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        FirstName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Groups(String.class, Access.Read, Access.Read, Access.Update, Access.Add, Access.Delete),
        Id(Integer.class, Access.Read, Access.Read, Access.Update, Access.Add, Access.Delete),
        JobTitle(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        LastName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        LastUpdated(Date.class, Access.Read),
        LastUpdatedBy(Integer.class, Access.Read),
        MiddleName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Nickname(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        OwnerID(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Password(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone1Ext(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone1Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone2Ext(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone2Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone3(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone3Ext(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone3Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone4(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone4Ext(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone4Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone5(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone5Ext(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Phone5Type(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        PostalCode(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        PostalCode2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        PostalCode3(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ReferralCode(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        SpouseName(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        State(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        State2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        State3(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        StreetAddress1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        StreetAddress2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Suffix(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Title(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Username(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        Validated(String.class, Access.Read),
        Website(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ZipFour1(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ZipFour2(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
        ZipFour3(String.class, Access.Read, Access.Update, Access.Add, Access.Delete)
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
        }    }
}
