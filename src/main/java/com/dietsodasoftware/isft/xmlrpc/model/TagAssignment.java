package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @link http://help.infusionsoft.com/developers/tables/contactgroupassign
 *
 * This doesn't work, though it complies with the docs.  Server always throws - this might be server-side bug.
 *
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/28/13
 * Time: 10:03 PM
 */
@TableName(table = "ContactGroupAssign")
public class TagAssignment extends Model {

    public TagAssignment(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        final List fields = new LinkedList();
        fields.addAll(Arrays.asList(Field.values()));
        fields.addAll(joinedFields());

        return Collections.unmodifiableCollection(fields);
    }

    public NamedField joinedContactField(final Contact.Field joined){
        return new NamedField(){

            @Override
            public String name() {
                return "Contact." + joined.name();
            }

            @Override
            public Class<?> typeClass() {
                return joined.typeClass();
            }

            @Override
            public Collection<Access> getAccess() {
                return Collections.unmodifiableList(Arrays.asList(Access.Read));
            }

            @Override
            public boolean hasAccess(Access access) {
                return getAccess().contains(access);
            }
        };
    }

    private List<NamedField> joinedFields(){
        final List<NamedField> fields = new LinkedList<NamedField>();
        for(Contact.Field field: Contact.Field.values()){
            switch (field){
                case AccountId:
                case LeadSourceId:
                case Password:
                case Username:
                case Validated:
                    continue;
                default:
                    fields.add(joinedContactField(field));
            }
        }

        return fields;
    }


    public enum Field implements NamedField<TagAssignment> {
        GroupId(Integer.class, Access.Read),
        ContactGroup(String.class, Access.Read),  // This is the tag name
        DateCreated(Date.class, Access.Read),
        ContactId(Integer.class, Access.Read)
        ;

        private final Class<?> fieldClass;
        private final List<Access> fieldAccess;
        private boolean isJoinedField = true;

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
}
