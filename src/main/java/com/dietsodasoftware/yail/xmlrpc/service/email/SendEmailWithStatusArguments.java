package com.dietsodasoftware.yail.xmlrpc.service.email;

public class SendEmailWithStatusArguments {
    private SendEmailWithStatusArguments(){}

    String fromAddress;
    String toAddresses;
    String ccAddresses;
    String bccAddresses;
    String subject;
    String htmlBody;
    String textBody;

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final SendEmailWithStatusArguments args;
        private Builder(){
            args = new SendEmailWithStatusArguments();
        }

        public SendEmailWithStatusArguments build(){
            verifyNotNull(args.fromAddress, "From Address");
            verifyNotNull(args.toAddresses, "To Addresses");
            verifyNotNull(args.subject, "Subject");
            verifyNotNull(args.textBody, "Text Body");

            return args;
        }

        public Builder fromAddress(String fromAddress){
            args.fromAddress = fromAddress;
            return this;
        }

        public Builder toAddresses(String toAddresses){
            args.toAddresses = toAddresses;
            return this;
        }

        public Builder ccAddresses(String ccAddresses){
            args.ccAddresses = ccAddresses;
            return this;
        }

        public Builder bccAddresses(String bccAddresses){
            args.bccAddresses = bccAddresses;
            return this;
        }

        public Builder subject(String subject){
            args.subject = subject;
            return this;
        }

        public Builder htmlBody(String htmlBody){
            args.htmlBody = htmlBody;
            return this;
        }

        public Builder textBody(String textBody){
            args.textBody = textBody;
            return this;
        }

        private void verifyNotNull(Object object, String name){
            if(object == null){
                throw new IllegalArgumentException("Argument " + name + " cannot be null");
            }
        }

    }
}
