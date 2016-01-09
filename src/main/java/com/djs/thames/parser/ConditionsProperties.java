package com.djs.thames.parser;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConditionsProperties {

    static final Logger logger = LogManager.getLogger(ConditionsProperties.class.getName());

    private static final String conditionsUrlName = "conditionsUrl";
    private static final String sqlServerDriverName = "sqlServerDriver";
    private static final String sqlConnectionName = "sqlConnection";

    private static String conditionsUrl = "";
    private static String sqlServerDriver = "";
    private static String sqlConnection = "";

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

            conditionsUrl = prop.getProperty(conditionsUrlName);
            sqlServerDriver = prop.getProperty(sqlServerDriverName);
            sqlConnection = prop.getProperty(sqlConnectionName);

        } catch (Exception e) {
            logger.error("Failed to load properties");
            logger.error(e);
        } finally {
            try {
                inputStream.close();
            }
            catch(IOException ex){
                logger.error("Exception whilst closing input stream");
                logger.error(ex);
            }
        }
    }
}