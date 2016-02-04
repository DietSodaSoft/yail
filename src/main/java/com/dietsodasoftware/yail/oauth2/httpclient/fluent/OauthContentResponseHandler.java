package com.dietsodasoftware.yail.oauth2.httpclient.fluent;

import com.dietsodasoftware.yail.oauth2.client.errors.OauthProtocolError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentHack;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *  almost identical to org.apache.http.client.fluent.ContentResponseHandler, but gets the entity body before throwing
 *  upon http errors.
 *
 *  This is basically the same thing as fluent's ContentResponseHandler.  However, this knows about infusionsoft's
 *  oauth responses and tries to give better reasons in the exception message, rather than requiring inspecting
 *  the body of the response entity.
 *
 */
public class OauthContentResponseHandler implements ResponseHandler<Content> {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new MrBeanModule());

    @Override
    public Content handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            final byte[] entityBody = EntityUtils.toByteArray(entity);
            final Content content = ContentHack.becauseScoping(entityBody, ContentType.getOrDefault(entity));

            if (statusLine.getStatusCode() >= 300) {
                final String contentAsString = content.asString();
                final OauthProtocolError error = mapper.readValue(contentAsString, OauthProtocolError.class);
                final String errorString = statusLine.getReasonPhrase() + ". " + error.getError() + ": " + error.getErrorDescription();

                throw new HttpResponseException(statusLine.getStatusCode(),
                        errorString);
            }
            if (entity != null) {
                return content;
            } else {
                return Content.NO_CONTENT;
            }

        }
}
