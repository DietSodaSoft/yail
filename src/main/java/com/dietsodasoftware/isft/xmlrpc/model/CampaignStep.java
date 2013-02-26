package com.dietsodasoftware.isft.xmlrpc.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/CampaignStep.html
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:33 PM
 */
public class CampaignStep extends Model {

    public CampaignStep(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class),
        CampaignId(Integer.class),
        TemplateId(Integer.class),
        StepStatus(String.class),
        StepTitle(String.class)
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
