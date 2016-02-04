package com.dietsodasoftware.yail.xmlrpc.service.email;

import java.util.LinkedList;
import java.util.List;

public class SendTemplateWithStatusArguments {
    private SendTemplateWithStatusArguments(){
        contactList = new LinkedList<Integer>();
    }

    final List<Integer> contactList;
    Integer templateId;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final SendTemplateWithStatusArguments args;
        private Builder(){
            args = new SendTemplateWithStatusArguments();
        }

        public SendTemplateWithStatusArguments build(){
            verifyNotNull(args.templateId, "Template Id");
            return args;
        }

        public Builder addContactId(int contactId){
            args.contactList.add(contactId);
            return this;
        }

        public Builder templateId(int templateId){
            args.templateId = templateId;
            return this;
        }

        private void verifyNotNull(Object object, String name){
            if(object == null){
                throw new IllegalArgumentException("Argument " + name + " cannot be null");
            }
        }

    }
}
