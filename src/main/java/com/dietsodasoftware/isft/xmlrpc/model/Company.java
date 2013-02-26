package com.dietsodasoftware.isft.xmlrpc.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/Company.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 7:20 PM
 */
public class Company extends Model {

    public Company(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }


    public enum Field implements NamedField {
        Address1Type(String.class),
        Address2Street1(String.class),
        Address2Street2(String.class),
        Address2Type(String.class),
        Address3Street1(String.class),
        Address3Street2(String.class),
        Address3Type(String.class),
        Anniversary(Date.class),
        AssistantName(String.class),
        AssistantPhone(String.class),
        BillingInformation(String.class),
        Birthday(Date.class),
        City(String.class),
        City2(String.class),
        City3(String.class),
        Company(String.class),
        AccountId(Integer.class),
        CompanyID(Integer.class),
        ContactNotes(String.class),
        ContactType(String.class),
        Country(String.class),
        Country2(String.class),
        Country3(String.class),
        CreatedBy(Integer.class),
        DateCreated(Date.class),
        Email(String.class),
        EmailAddress2(String.class),
        EmailAddress3(String.class),
        Fax1(String.class),
        Fax1Type(String.class),
        Fax2(String.class),
        Fax2Type(String.class),
        FirstName(String.class),
        Groups(String.class),
        Id(Integer.class),
        JobTitle(String.class),
        LastName(String.class),
        LastUpdated(Date.class),
        LastUpdatedBy(Integer.class),
        MiddleName(String.class),
        Nickname(String.class),
        OwnerID(Integer.class),
        Password(String.class),
        Phone1(String.class),
        Phone1Ext(String.class),
        Phone1Type(String.class),
        Phone2(String.class),
        Phone2Ext(String.class),
        Phone2Type(String.class),
        Phone3(String.class),
        Phone3Ext(String.class),
        Phone3Type(String.class),
        Phone4(String.class),
        Phone4Ext(String.class),
        Phone4Type(String.class),
        Phone5(String.class),
        Phone5Ext(String.class),
        Phone5Type(String.class),
        PostalCode(String.class),
        PostalCode2(String.class),
        PostalCode3(String.class),
        ReferralCode(String.class),
        SpouseName(String.class),
        State(String.class),
        State2(String.class),
        State3(String.class),
        StreetAddress1(String.class),
        StreetAddress2(String.class),
        Suffix(String.class),
        Title(String.class),
        Username(String.class),
        Validated(String.class),
        Website(String.class),
        ZipFour1(String.class),
        ZipFour2(String.class),
        ZipFour3(String.class)
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
