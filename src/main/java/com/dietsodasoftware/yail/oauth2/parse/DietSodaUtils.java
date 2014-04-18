package com.dietsodasoftware.yail.oauth2.parse;

/**
 * User: wendel.schultz
 * Date: 4/18/14
 */
public class DietSodaUtils {

    public static final String MASHERY_CLIENT_ID = "Your mashery application client ID here";
    public static final String MASHERY_CLIENT_SECRET = "Your mashery client secret here";

    private static final String PARSE_APPLICATION_ID = "Your own Parse.com application ID here";
    private static final String PARSE_API_KEY = "Your own Parse.com API Key";

    public static ParseConfiguration parse(){
        return new ParseConfiguration(PARSE_APPLICATION_ID, PARSE_API_KEY);
    }

}
