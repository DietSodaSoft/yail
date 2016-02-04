package com.dietsodasoftware.yail.xmlrpc.config;

import com.dietsodasoftware.yail.xmlrpc.client.YailProfile;
import com.dietsodasoftware.yail.xmlrpc.client.YailProfileBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * This class assembles a {@link Properties} object from an {@link InputStream}, {@link URL}, or a file on this class'
 * classpath.  It then looks for specific properties to create {@link YailProfile}s.
 *
 * See {@code yail.properties} for documentation on how to correctly provide the properties.
 *
 * User: wendel.schultz
 * Date: 9/5/14
 */
public class YailPropertiesConfiguration {
    private final static String DEFAULT_CONFIG_FILENAME = "yail.properties";

    private static final String PROP_DSS_VENDOR_KEY = "com.dietsodasoftware.yail.vendorkey";

    private static final String PROP_APP_NAME = "com.dietsodasoftware.yail.appname";
    private static final String PROP_APP_APIKEY_PREFIX = "com.dietsodasoftware.yail.apikey";
    private static final String PROP_APP_LOCATION_PREFIX = "com.dietsodasoftware.yail.location";

    private static final String PROP_CAS_USERNAME_KEY = "com.dietsodasoftware.yail.account-central-username";
    private static final String PROP_CAS_PASSWORD_KEY = "com.dietsodasoftware.yail.account-central-password";


    private final Properties configurationProperties;

    public YailPropertiesConfiguration() throws IOException {
        this(DEFAULT_CONFIG_FILENAME);
    }

    public YailPropertiesConfiguration(String classpathFileName) throws IOException {
        if(classpathFileName == null){
            throw new IllegalArgumentException("Must provide a file found on the classpath");
        }

        configurationProperties = loadPropertiesFromClasspath(classpathFileName);
    }

    public YailPropertiesConfiguration(URL url) throws IOException {
        if(url == null){
            throw new IllegalArgumentException("Must provide a URL to source yail properties from");
        }
        configurationProperties = loadProperties(url.openStream());
    }

    public YailPropertiesConfiguration(Properties properties){
        if(properties == null){
            throw new IllegalArgumentException("Must provide a properties instance");
        }

        this.configurationProperties = properties;
    }

    /**
     * This is generally intended to be used to select the classloader from which you want to find your
     * properties file.  The net result: give this InputStream to a Properties object to load.
     */
    public YailPropertiesConfiguration(InputStream is) throws IOException {
        if(is == null){
            throw new IllegalArgumentException("Must provide an input stream to source yail properties from");
        }

        configurationProperties = loadProperties(is);
    }

    /**
     *  Create a {@link YailProfile} for the app identified in the property named
     *  {@code com.dietsodasoftware.yail.appname}.
     */
    public YailProfile createProfile(){
        final String appName = configurationProperties.getProperty(PROP_APP_NAME);
        return createProfileForApp(appName);
    }

    public YailProfile createProfileForApp(final String appName){
        if(appName == null){
            throw new IllegalArgumentException("Must provide appName to get a app profile");
        }

        final String apiKey = configurationProperties.getProperty(PROP_APP_APIKEY_PREFIX + "." + appName);
        final String location = configurationProperties.getProperty(PROP_APP_LOCATION_PREFIX + "." + appName);
        final String vendorKey = getVendorKey();
        final String username = getUsername();
        final String password = getPassword();

        // it is OK if some of these are null, because the builder asserts valid configuration.
        return new YailProfileBuilder(appName)
                .usingApiKey(apiKey)
                .usingVendorKey(vendorKey, username, password)
                .atLocation(location)
                .build();
    }

    public String getUsername(){
        return configurationProperties.getProperty(PROP_CAS_USERNAME_KEY);
    }

    public String getPassword(){
        return configurationProperties.getProperty(PROP_CAS_PASSWORD_KEY);
    }

    public String getVendorKey(){
        return configurationProperties.getProperty(PROP_DSS_VENDOR_KEY);
    }

    private Properties loadPropertiesFromClasspath(String classpathFileName) throws IOException {
        if(classpathFileName == null){
            throw new IllegalArgumentException("Must provide a file found on the classpath");
        }

        return loadProperties(getClass().getClassLoader().getResourceAsStream(classpathFileName));
    }

    private static Properties loadProperties(InputStream is) throws IOException {
        final Properties configurationProperties = new Properties();
        configurationProperties.load(is);

        return configurationProperties;
    }
}
