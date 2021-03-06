package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/21/13
 * Time: 12:08 AM
 */
@TableName(table = "DataFormField")
public class CustomField extends Model {

    public enum Model {
        Contact("Contact", -1),
        Company("Company", -6),
        Affiliate("Affiliate", -3),
        Task("ContactAction", -5),
        Appointment("ContactAction", -5),
        Note("ContactAction", -5),
        Order("Job", -9),
        Subscription("JobRecurring", -10),
        Opportunity("Opportunity", -4);

        private final Integer modelTypeDbId;
        private final String modelTypeDbName;
        private Model(String modelTypeDbName, Integer modelTypeDbId){
            this.modelTypeDbName = modelTypeDbName;
            this.modelTypeDbId = modelTypeDbId;
        }

        private static Map<String, Model> modelNames = new HashMap<String, Model>();
        private static Map<Integer, Model> modelDbIds = new HashMap<Integer, Model>();
        static {
            for(Model model: Model.values()){
                modelNames.put(model.modelTypeDbName, model);
                modelDbIds.put(model.modelTypeDbId, model);
            }
        }

        public Integer getModelTypeDbId(){
            return modelTypeDbId;
        }

        public String getModelTypeDbName(){
            return modelTypeDbName;
        }

        public static Model forDbName(String dbName){
            return modelNames.get(dbName);
        }

        public static Model forDbId(Integer id){
            return modelDbIds.get(id);
        }
    }

    public enum FormFieldFormatting {
                PhoneNumber(1, "Phone"),
                SocialSecurityNumber(2, "SSN"),
                Currency(3),
                Percent(4),
                State(5),
                YesNo(6, "YesNo"),
                Year(7),
                Month(8),
                DayOfWeek(9),
                Name(10),
                DecimalNumber(11, "NumberDecimal"),
                WholeNumber(12, "Number"),
                Date(13),
                DateAndTime(14, "DateTime"),
                Text(15),
                TextArea(16, "TextArea"),
                ListBox(17, "UserMultiSelect"),
                Website(18),
                Email(19),
                Radio(20),
                Dropdown(21, "Select"),
                User(22),
                Drilldown(23);

        private static Map<String, FormFieldFormatting> formatNames = new HashMap<String, FormFieldFormatting>();
        private static Map<Integer, FormFieldFormatting> formatDbIds = new HashMap<Integer, FormFieldFormatting>();
        static {
            for(FormFieldFormatting format: FormFieldFormatting.values()){
                formatNames.put(format.dataFormFieldTypeName, format);
                formatDbIds.put(format.dataFormFieldTypeDbId, format);
            }
        }


        private final Integer dataFormFieldTypeDbId;
        private final String dataFormFieldTypeName;
        private FormFieldFormatting(Integer typeDbId){
            this(typeDbId, null);
        }

        private FormFieldFormatting(Integer typeDbId, String typeDbName){
            if(typeDbName == null){
                this.dataFormFieldTypeName = name();
            } else {
                this.dataFormFieldTypeName = typeDbName;
            }
            this.dataFormFieldTypeDbId = typeDbId;
        }

        public Integer getDbTypeId(){
            return dataFormFieldTypeDbId;
        }

        public String getDbTypeName(){
            if(this.dataFormFieldTypeName == null){
                return name();
            }
            return dataFormFieldTypeName;
        }

        public static FormFieldFormatting forDbName(String dbName){
            return formatNames.get(dbName);
        }

        public static FormFieldFormatting forDbId(Integer id){
            return formatDbIds.get(id);
        }    }

    public CustomField(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        FormId(Integer.class, Access.Read),  // this is the model db type ID
        DataType(String.class, Access.Read),
        GroupId(Integer.class, Access.Read, Access.Update), // This is the header Id
        Name(String.class, Access.Read, Access.Update),
        Label(String.class, Access.Read, Access.Update),
        DefaultValue(String.class, Access.Read, Access.Update),
        Values(String.class, Access.Read, Access.Update),
        ListRows(Integer.class, Access.Read, Access.Update)
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

    public static Builder<CustomField> builder(){
        return new Builder<CustomField>(CustomField.class);
    }

    public FormFieldFormatting getFieldFormat(){
        final Integer formId = getFieldValue(Field.DataType);
        return FormFieldFormatting.forDbId(formId);
    }

    public Model getEntityModel(){
        final Integer modelId = getFieldValue(Field.DataType);
        return Model.forDbId(modelId);
    }
}
