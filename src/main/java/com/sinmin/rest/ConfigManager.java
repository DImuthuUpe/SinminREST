package com.sinmin.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    public static final String ORACLE_DB_DRIVER = "ORACLE_DB_DRIVER";
    public static final String ORACLE_DB_CONNECTION = "ORACLE_DB_CONNECTION";
    public static final String ORACLE_DB_USER = "ORACLE_DB_USER";
    public static final String ORACLE_DB_PASSWORD = "ORACLE_DB_PASSWORD";
    public static final String CASSANDRA_SERVER_IP = "CASSANDRA_SERVER_IP";
    public static final String LDAP_CONTEXT_FACTORY="LDAP_CONTEXT_FACTORY";
    public static final String LDAP_PROVIDER_URL="LDAP_PROVIDER_URL";
    public static final String LDAP_SECURITY_PRINCIPAL="LDAP_SECURITY_PRINCIPAL";

    public static String getProperty(String propertyName){

        InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
