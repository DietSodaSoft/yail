package com.dietsodasoftware.isft.xmlrpc.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/Campaignee.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:31 PM
 */
public class Campaignee extends Model {

    public Campaignee(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        CampaignId(Integer.class),
        Status(String.class), // this is an enum in the DB
        Campaign(String.class),
        ContactId(Integer.class)
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
