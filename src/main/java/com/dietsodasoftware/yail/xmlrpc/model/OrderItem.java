package com.dietsodasoftware.yail.xmlrpc.model;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.TableName;

import java.util.*;

/**
 * http://help.infusionsoft.com/developers/tables/orderitem
 *
 * @author Victor M. Miller
 */
@TableName(table = "OrderItem")
public class OrderItem extends Model {

    public OrderItem(Map<String, Object> model) {
        super(model);
    }

    @Override
    public Collection<? extends NamedField> allFields(){
        return Collections.unmodifiableCollection(Arrays.asList(Field.values()));
    }

    public enum Field implements NamedField {
        Id(Integer.class, Access.Read),
        OrderId(Integer.class, Access.Update, Access.Add, Access.Read),
        ProductId(Integer.class, Access.Update, Access.Add, Access.Read),
        SubscriptionPlanId(Integer.class, Access.Update, Access.Add, Access.Read),
        ItemName(String.class, Access.Update, Access.Add, Access.Read),
        Qty(Integer.class, Access.Update, Access.Add, Access.Read),
        CPU(Double.class, Access.Update, Access.Add, Access.Read),
        PPU(Double.class, Access.Update, Access.Add, Access.Read),
        ItemDescription(String.class, Access.Update, Access.Add, Access.Read),
        ItemType(Integer.class, Access.Update, Access.Add, Access.Read);

        private final Class<?> fieldClass;
        private final List<Access> fieldAccess;

        private Field(Class<?> fieldClass, Access... fieldAccess) {
            if(fieldAccess == null || fieldAccess.length == 0){ throw new RuntimeException("Invalid null fieldAccess argument"); }
            this.fieldClass = fieldClass;
            this.fieldAccess = Arrays.asList(fieldAccess);
        }

        public Class<?> typeClass() {
            return fieldClass;
        }

        public boolean hasAccess(Access access){
            return fieldAccess.contains(access);
        }

        public Collection<Access> getAccess(){
            return Collections.unmodifiableList(fieldAccess);
        }
    }

    public enum ItemType {
        UnknownType(0, "Unknown Type"),
        Shipping(1, "Shipping"),
        Tax(2, "Tax"),
        ServiceAndMisc(3, "Service and Misc"),
        Product(4, "Product"),
        UpsellProduct(5, "Upsell Product"),
        FinanceCharge(6, "Finacne Charge"),
        Special(7, "Special"),
        Program(8, "Program"),
        SubscriptionPlan(9, "SubscriptionPlan"),
        SpecialFreeTrialDays(10, "Special: Free Trial Days"),
        SpecialOrderTotal(11, "Special: Order Total"),
        SpecialProduct(12, "Special: Product"),
        SpecialCategory(13, "Special: Category"),
        SpecialShipping(14, "Special: Shipping");

        private Integer typeIdentifier;
        private String description;

        private ItemType(Integer typeIdentifier, String description) {
            this.typeIdentifier = typeIdentifier;
            this.description = description;
        }

        public static ItemType getByIdentifier(Integer identifier) {
            for (ItemType itemType : values()) {
                if (itemType.typeIdentifier.equals(identifier)) {
                    return itemType;
                }
            }

            return UnknownType;
        }

        public String getDescription() {
            return description;
        }
    }
}