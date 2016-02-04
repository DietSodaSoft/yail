package org.apache.http.client.fluent;

import org.apache.http.entity.ContentType;

/**
 * Created by wendel.schultz on 2/4/16.
 */
public class ContentHack {

    public static Content becauseScoping(byte[] bytes, ContentType orDefault) {
        if(bytes == null) {
            bytes = new byte[]{};
        }
        return new Content(bytes, orDefault);
    }
}
