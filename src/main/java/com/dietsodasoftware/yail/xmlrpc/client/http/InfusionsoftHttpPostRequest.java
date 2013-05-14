package com.dietsodasoftware.yail.xmlrpc.client.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/7/13
 * Time: 12:57 PM
 */
public abstract class InfusionsoftHttpPostRequest<T> extends InfusionsoftHttpRequest<T> {

    final List<NameValuePair> entityParameters = new LinkedList<NameValuePair>();

    protected InfusionsoftHttpPostRequest(String path) {
        super(path);
    }

    public HttpEntity getPostBody(){
        return new UrlEncodedFormEntity(getEntityParameters(), Consts.UTF_8);
    }

    public List<NameValuePair> getEntityParameters() {
        return entityParameters;
    }

    public InfusionsoftHttpRequest<T> addEntityParameter(final String name, final String value) {
        urlParameters.add(new BasicNameValuePair(name, value));
        return this;
    }

}
