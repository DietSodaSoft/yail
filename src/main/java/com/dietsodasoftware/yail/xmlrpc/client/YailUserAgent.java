package com.dietsodasoftware.yail.xmlrpc.client;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * User: wendel.schultz
 * Date: 12/19/13
 */
class YailUserAgent {
    private static final String MANIFEST_YAIL_PROJECT_NAME = "YAIL Infusionsoft SDK";

    private static final String MANIFEST_IMPLEMENTATION_DESCRIPTION = "Implementation-Project-Description";
    private static final String MANIFEST_VERSION_ATTRIBUTE = "Implementation-Version";
    private static final String MANIFEST_BUILD_ATTRIBUTE = "Implementation-Build";
    private static final String MANIFEST_SCM_INFO = "Implementation-SCM-Revision";
    private static final String DEFAULT_USER_AGENT = "YAIL Infusionsoft SDK, (c) Diet Soda Software";


    private final String userAgent;
    YailUserAgent(){
        String userAgent = DEFAULT_USER_AGENT;
        try {
            final Manifest mf = findManifest();

            final Attributes attrs = mf.getMainAttributes();

            final String projectDescription = attrs.getValue(MANIFEST_IMPLEMENTATION_DESCRIPTION);
            if(projectDescription != null){
                userAgent = projectDescription;
            }

            final String version = attrs.getValue(MANIFEST_VERSION_ATTRIBUTE);
            if(version != null){
                userAgent += "; Version " + version;
            }

            final String build = attrs.getValue(MANIFEST_BUILD_ATTRIBUTE);
            if(build != null){
                userAgent += "; " + build;
            }

            final String scm = attrs.getValue(MANIFEST_SCM_INFO);
            if(scm != null){
                userAgent += "; " + scm;
            }
        } catch (IOException e) {
            // can't get it .. what can I do?
        }

        this.userAgent = userAgent;
    }

    private static Manifest findManifest() throws IOException {
        Enumeration<URL> resources = YailUserAgent.class.getClassLoader()
                .getResources("META-INF/MANIFEST.MF");
        while (resources.hasMoreElements()) {
            try {
                final Manifest manifest = new Manifest(resources.nextElement().openStream());
                // check that this is your manifest and do what you need or get the next one
                final Attributes attrs = manifest.getMainAttributes();
                final String projectDescription = attrs.getValue(MANIFEST_IMPLEMENTATION_DESCRIPTION);
                if(projectDescription != null && projectDescription.startsWith(MANIFEST_YAIL_PROJECT_NAME)){
                    return manifest;
                }

            } catch (IOException E) {
                // handle
            }
        }

        return new Manifest();
    }

    String getUserAgentString(){
        return userAgent;
    }

    @Override
    public String toString(){
        return getUserAgentString();
    }
}
