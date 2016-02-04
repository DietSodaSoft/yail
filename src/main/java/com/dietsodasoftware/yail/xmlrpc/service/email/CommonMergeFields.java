package com.dietsodasoftware.yail.xmlrpc.service.email;

/**
 * User: wendel.schultz
 * Date: 1/13/14
 */
public class CommonMergeFields {
    public enum Contact implements MergeFieldName{
        Email("Contact.Email")

        ;

        private final String rawValue;
        private Contact(String rawValue){
            this.rawValue = rawValue;
        }

        public String getMergeFieldName(){
            return rawValue;
        }
    }

}
