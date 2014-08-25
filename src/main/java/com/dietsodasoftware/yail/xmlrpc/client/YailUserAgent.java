package com.dietsodasoftware.yail.xmlrpc.client;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * User: wendel.schultz
 * Date: 12/19/13
 */
class YailUserAgent {
    private static final String MANIFEST_VERSION_ATTRIBUTE = "Implementation-Version";
    private static final String MANIFEST_BUILD_ATTRIBUTE = "Implementation-Build";
    private static final String MANIFEST_SCM_INFO = "Implementation-SCM-Revision";
    private static final String DEFAULT_USER_AGENT = "YAIL Infusionsoft SDK, (c) Diet Soda Software";


    private final String userAgent;
    YailUserAgent(){
        final Manifest mf = new Manifest();
        String userAgent = DEFAULT_USER_AGENT;
        try {
            mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));

            final Attributes attrs = mf.getMainAttributes();

            final Object version = attrs.get(MANIFEST_VERSION_ATTRIBUTE);
            if(version != null){
                userAgent += ". Version " + version.toString();
            }

            final Object build = attrs.get(MANIFEST_BUILD_ATTRIBUTE);
            if(build != null){
                userAgent += ", " + build.toString();
            }

            final Object scm = attrs.get(MANIFEST_SCM_INFO);
            if(scm != null){
                userAgent += ", " + scm.toString();
            }
        } catch (IOException e) {
            // can't get it .. what can I do?
        }

        this.userAgent = userAgent;
    }

    String getUserAgentString(){
        return userAgent;
    }

    @Override
    public String toString(){
        return getUserAgentString();
    }
}
