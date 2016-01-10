package com.djs.thames.parser;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ConditionsProperties {

    static final Logger logger = LogManager.getLogger(ConditionsProperties.class.getName());

    private static final String conditionsUrlName = "conditionsUrl";
    private static final String sqlServerDriverName = "sqlServerDriver";
    private static final String sqlConnectionName = "sqlConnection";

    public static final String EMAIL_HOST_NAME = "email.hostName";
    public static final String EMAIL_SMTP_PORT = "email.smtpPort";
    public static final String EMAIL_USER_NAME = "email.userName";
    public static final String EMAIL_PASSWORD = "email.password";
    public static final String EMAIL_SSL = "email.ssl";
    public static final String EMAIL_FROM = "email.from";

    private static String conditionsUrl = "";
    private static String sqlServerDriver = "";
    private static String sqlConnection = "";

    private static Map<String, String> properties = new HashMap<String, String>();

    public static String getConditionsUrl() {
        return conditionsUrl;
    }

    public static String getSqlServerDriver() {
        return sqlServerDriver;
    }

    public static String getSqlConnection() {
        return sqlConnection;
    }

    public static void initialise(){

        InputStream inputStream = null;

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new ConditionsProperties().getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                logger.error("Property file not found on classpath: {}", propFileName );
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            conditionsUrl = getProperty(prop, conditionsUrlName);
            sqlServerDriver = getProperty(prop, sqlServerDriverName);
            sqlConnection = getProperty(prop, sqlConnectionName);

            Enumeration propertyNames = prop.propertyNames();
            while(propertyNames.hasMoreElements()) {
                String key = propertyNames.nextElement().toString();
                String value = prop.getProperty(key);
                properties.put(key, value);
            }

        } catch (Exception e) {
            logger.error("Failed to load properties");
            logger.error(e);
        } finally {
            try {
                inputStream.close();
            }
            catch(Exception ex){
                logger.error("Exception whilst closing input stream");
                logger.error(ex);
            }
        }
    }

    public static String getProperty(String propertyName){

        if( System.getProperty(propertyName) != null){
            return System.getProperty(propertyName);
        }

        if( properties.containsKey(propertyName) ) {
            return properties.get(propertyName);
        }
        return "";
    }

    public static int getPropertyInt(String propertyName, int defaultValue){

        String property = getProperty(propertyName);

        if( property != null && property.length() > 0) {

            return Integer.parseInt(property);
        }

        return defaultValue;
    }

    public static Boolean getPropertyBool(String propertyName, Boolean defaultValue){

        String property = getProperty(propertyName);

        if( property != null && property.length() > 0) {

            if( property.toLowerCase().equals("t") ||
                property.toLowerCase().equals("true") ){

                return true;
            }
        }

        return defaultValue;
    }

    private static String getProperty(Properties properties, String propertyName){

        if( System.getProperty(propertyName) != null){
            return System.getProperty(propertyName);
        }

        return properties.getProperty(propertyName);
    }

}