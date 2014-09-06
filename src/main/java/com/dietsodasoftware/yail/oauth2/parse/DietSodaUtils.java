package com.dietsodasoftware.yail.oauth2.parse;

/**
 * User: wendel.schultz
 * Date: 4/18/14
 */
public class DietSodaUtils {

    // this is for Chris' proofing
//    public static final String MASHERY_CLIENT_ID = "7xz748dqbcctj4whx2kkm4kh";
//    public static final String MASHERY_CLIENT_SECRET = "RJUEKgUNVC";

    // my creds
    public static final String MASHERY_CLIENT_ID = "nwhg42jrzr9498zdxkup9ww9";
    public static final String MASHERY_CLIENT_SECRET = "qdUMG7cFcM";

    private static final String PARSE_APPLICATION_ID = "z8NmDvVsa7WPBpqsQGXtrqZRyVlokCNoEb40BdOE";
    private static final String PARSE_API_KEY = "m1KOhmEbRcPlCkvGHiPKN71Y2H1zPeXZnACaB9H6";

    public static ParseConfiguration parse(){
        return new ParseConfiguration(PARSE_APPLICATION_ID, PARSE_API_KEY);
    }

}
