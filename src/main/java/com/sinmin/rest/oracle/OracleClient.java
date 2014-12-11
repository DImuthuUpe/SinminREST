package com.sinmin.rest.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by dimuthuupeksha on 12/11/14.
 */
public class OracleClient {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    //private static final String DB_CONNECTION = "jdbc:oracle:thin:@//localhost:1521/PDB1";
    //private static final String DB_USER = "sinmin";
    //private static final String DB_PASSWORD = "sinmin";

    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//192.248.15.239:1522/corpus.sinmin.com";
    private static final String DB_USER = "sinmin";
    private static final String DB_PASSWORD = "Sinmin1234";

    private static Connection dbConnection = null;


    public static Connection getDBConnection() {

        if (dbConnection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                dbConnection = DriverManager.getConnection(
                        DB_CONNECTION, DB_USER, DB_PASSWORD);
                return dbConnection;
            } catch (Exception e) {
                //logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return dbConnection;
    }







}
