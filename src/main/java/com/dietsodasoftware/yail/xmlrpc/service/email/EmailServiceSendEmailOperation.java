package com.dietsodasoftware.yail.xmlrpc.service.email;

import com.dietsodasoftware.yail.xmlrpc.client.annotations.ArgumentValidator;
import com.dietsodasoftware.yail.xmlrpc.client.annotations.InfusionsoftRpc;
import com.dietsodasoftware.yail.xmlrpc.model.Contact;
import com.dietsodasoftware.yail.xmlrpc.service.SimpleRpcServiceOperation;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * User: wendel.schultz
 * Date: 1/13/14
 */
@InfusionsoftRpc(service = "APIEmailService", method = "sendEmail")
public class EmailServiceSendEmailOperation extends SimpleRpcServiceOperation<Boolean> {

    private List<Integer> contactIds = new LinkedList<Integer>();
    private String fromAddress;
    private MergeFieldName toAddress = CommonMergeFields.Contact.Email;
    private List<String> ccAddresses = new LinkedList<String>();
    private List<String> bccAddresses = new LinkedList<String>();
    private EmailContentType contentType = EmailContentType.Text;
    private String subject;
    private String htmlBody;
    private String textBody;
    private Long templateId;

    @Override
    protected List<Object> getOperationParameters() {
        final List<Object> parameters = new LinkedList<Object>();

        final String ccAddressString;
        final String bccAddressString;

        if(ccAddresses.size() > 0){
            ccAddressString = StringUtils.join(ccAddresses, ",");
        } else {
            ccAddressString = "";
        }

        if(bccAddresses.size() > 0){
            bccAddressString = StringUtils.join(bccAddresses, ",");
        } else {
            bccAddressString = "";
        }

        parameters.add(contactIds.toArray());
        parameters.add(fromAddress);
        parameters.add(MergeFieldTemplate.getMergeFieldTemplate(toAddress));
        parameters.add(ccAddressString);
        parameters.add(bccAddressString);
        parameters.add(contentType.getContentTypeParameter());
        parameters.add(subject);
        parameters.add(htmlBody);
        parameters.add(textBody);

        if(templateId != null){
            parameters.add(String.valueOf(templateId));
        }

        return parameters;
    }

    @ArgumentValidator
    public void validate(){
        if(contactIds.size() == 0) throw new IllegalArgumentException("Must provide at least one contact");
        if(fromAddress == null) throw new IllegalArgumentException("Must provide from: address");
        if(toAddress == null) throw new IllegalArgumentException("Must provide to: address merge field");
        if(subject == null) throw new IllegalArgumentException("Must provide subject");
        if(htmlBody == null) throw new IllegalArgumentException("Must provide html body");
        if(textBody == null) throw new IllegalArgumentException("Must provide text body");
    }


    public EmailServiceSendEmailOperation setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    public EmailServiceSendEmailOperation setToAddress(MergeFieldName toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public EmailServiceSendEmailOperation setContentType(EmailContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public EmailServiceSendEmailOperation setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailServiceSendEmailOperation setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
        return this;
    }

    public EmailServiceSendEmailOperation setTextBody(String textBody) {
        this.textBody = textBody;
        return this;
    }

    public EmailServiceSendEmailOperation setTemplateId(Long templateId) {
        this.templateId = templateId;
        return this;
    }

    public EmailServiceSendEmailOperation addContact(Contact contact){
        if(contact != null && contact.getFieldValue(Contact.Field.Id) != null){
            final Integer id = contact.getFieldValue(Contact.Field.Id);
            contactIds.add(id);
        }

        return this;
    }

    public EmailServiceSendEmailOperation addContact(Long contactId){
        if(contactId != null){
            contactIds.add(contactId.intValue());
        }

        return this;
    }

}
