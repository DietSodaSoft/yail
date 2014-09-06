package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @link http://developers.infusionsoft.com/dbDocs/ContactAction.html
 *
 */
@TableName(table = "ContactAction")
public class ContactAction extends Model {

	public ContactAction(Map<String, Object> model) {
		super(model);
	}
	
	@Override
	public Collection<? extends NamedField> allFields(){
		return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
	}
	

	public enum Field implements NamedField {
		Id(Integer.class, Access.Read),
		ContactId(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		OpportunityId(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		ActionType(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		ActionDescription(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		CreationDate(Date.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		CreationNotes(String.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		CompletionDate(Date.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		ActionDate(Date.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		EndDate(Date.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		PopupDate(Date.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		UserID(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		Accepted(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		CreatedBy(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		LastUpdated(Date.class, Access.Read),
		LastUpdatedBy(Integer.class, Access.Read),
		Priority(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete),
		IsAppointment(Integer.class, Access.Read, Access.Update, Access.Add, Access.Delete) // 1 means "yes", 0 means "no"
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

    public static Builder<ContactAction> builder(){
        return new Builder<ContactAction>(ContactAction.class);
    }
}
