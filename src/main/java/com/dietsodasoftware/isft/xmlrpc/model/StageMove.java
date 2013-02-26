package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/StageMove.html
 *
 * All of StageMove's fields are read-only.  FYI.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/25/13
 * Time: 8:48 PM
 */
@TableName(table = "StageMove")
public class StageMove extends Model {

    public StageMove(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Long.class),
        OpportunityId(Long.class),
        MoveDate(Date.class),
        MoveToStage(Long.class),
        MoveFromStage(Long.class),
        PrevStageMoveDate(Date.class),
        CreatedBy(Long.class),
        DateCreated(Date.class),
        UserId(Long.class)
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
