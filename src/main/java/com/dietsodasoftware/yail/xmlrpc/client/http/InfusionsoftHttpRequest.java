package com.dietsodasoftware.yail.xmlrpc.client.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.mrbean.MrBeanModule;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/7/13
 * Time: 12:58 PM
 */
abstract class InfusionsoftHttpRequest<T> {

    private final ObjectMapper objectMapper;

    final String requestPath;
    final List<NameValuePair> urlParameters = new LinkedList<NameValuePair>();
    protected InfusionsoftHttpRequest(String path){
        this.requestPath = path;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new MrBeanModule());
    }

    public String getRequestPath(){
        return requestPath;
    }

    abstract public T parseResponse(Response httpResponse) throws IOException;

    public List<NameValuePair> getUrlParameters() {
        return urlParameters;
    }

    public InfusionsoftHttpRequest<T> addUrlParameter(final String name, final String value) {
        urlParameters.add(new BasicNameValuePair(name, value));
        return this;
    }

    /**
     * Override if you'd like to assert that the combination of arguments is in/valid before the request is sent.
     */
    public void validateArguments(){

    }

    protected T parseResponseAsJsonObject(Class<T> clazz, Response httpResponse) throws IOException {
        final String contentAsString = httpResponse.returnContent().asString();
        return objectMapper.readValue(contentAsString, clazz);
    }

}
