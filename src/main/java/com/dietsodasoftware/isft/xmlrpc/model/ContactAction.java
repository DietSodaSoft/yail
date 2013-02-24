package com.dietsodasoftware.isft.xmlrpc.model;

import com.dietsodasoftware.isft.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

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
		Id(Integer.class),	
		ContactId(Integer.class),	
		OpportunityId(Integer.class),	
		ActionType(String.class),	
		ActionDescription(String.class),	
		CreationDate(Date.class),	
		CreationNotes(String.class),	
		CompletionDate(Date.class),	
		ActionDate(Date.class),	
		EndDate(Date.class),	
		PopupDate(Date.class),	
		UserID(Integer.class),	
		Accepted(Integer.class),	
		CreatedBy(Integer.class),	
		LastUpdated(Date.class),	
		LastUpdatedBy(Integer.class),	
		Priority(Integer.class),	
		IsAppointment(Integer.class) // 1 means "yes", 0 means "no"
		;
		private final Class<?> typeClass;
		private Field(Class<?> typeClass){
			this.typeClass = typeClass;
		}
		
		public Class<?> typeClass(){
			return typeClass;
		}
	}
}
