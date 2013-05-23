package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
        Id(Long.class, Access.Read),
        OpportunityId(Long.class, Access.Read),
        MoveDate(Date.class, Access.Read),
        MoveToStage(Long.class, Access.Read),
        MoveFromStage(Long.class, Access.Read),
        PrevStageMoveDate(Date.class, Access.Read),
        CreatedBy(Long.class, Access.Read),
        DateCreated(Date.class, Access.Read),
        UserId(Long.class, Access.Read)
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

    public static Builder<StageMove> builder(){
        return new Builder<StageMove>(StageMove.class);
    }
}
