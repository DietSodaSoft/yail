package com.dietsodasoftware.yail.oauth2.take1.parse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import org.apache.http.Header;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public class ParseConfiguration {
    private static final String HEADER_PARSE_CLIENT_ID = "X-Parse-Application-Id";
    private static final String HEADER_PARSE_API_KEY = "X-Parse-REST-API-Key";

    private final String parseClientId;
    private final String parseClientApiKey;
    private final ObjectMapper objectMapper;

    public ParseConfiguration(String applicationId, String apiKey){
        this.parseClientId = applicationId;
        this.parseClientApiKey = apiKey;

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.registerModule(new MrBeanModule());
    }

    public <T> T parseJsonResponse(String content, Class<T> targetType) throws IOException {
        return objectMapper.readValue(content, targetType);
    }

    public <T> T parseJsonResponse(InputStream content, Class<T> targetType) throws IOException {
        return objectMapper.readValue(content, targetType);
    }

    public Request configureRequest(Request request){
        return request.addHeader(parseApplicationHeader(parseClientId))
                      .addHeader(parseApiKeyHeader(parseClientApiKey))
                      .addHeader(acceptTypeJson());
    }

    public <T> Request configureRequest(Request request, T requestBody){
        if(requestBody == null) { throw new IllegalArgumentException("must provide request body"); }
        try {
            request = configureRequest(request)
                    .addHeader(contentTypeJson())
                    .bodyByteArray(objectMapper.writeValueAsBytes(requestBody));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Bad Parse.com handling from the SDK provider.  YAIL for the loss.", e);
        }

        return request;
    }

    private static Header parseApplicationHeader(String applicationId){
        return new BasicHeader(HEADER_PARSE_CLIENT_ID, applicationId);
    }

    private static Header parseApiKeyHeader(String apiKey){
        return new BasicHeader(HEADER_PARSE_API_KEY, apiKey);
    }

    private static Header contentTypeJson(){
        return new BasicHeader("Content-Type", "application/json");
    }

    private static Header acceptTypeJson(){
        return new BasicHeader("Accept", "application/json");
    }

}
