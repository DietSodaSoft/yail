package com.dietsodasoftware.yail.oauth2.client.tokens;

/**
 * Created by wendel.schultz on 2/4/16.
 *
 * The default one gives a random UUID by:
 *
 *    UUID.randomUUID().toString()
 *
 */
public interface CodeStateGenerator {

    public String createRequestState();

}
