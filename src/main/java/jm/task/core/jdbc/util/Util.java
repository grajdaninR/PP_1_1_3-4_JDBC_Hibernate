package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Util {
    private static Logger log = Logger.getLogger(Util.class.getName());

    private static final String DB_URL = "jdbc:mysql://localhost:3306/usersdb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "9219";

    private static Connection dbConnect;

    private Util() {}

    public static  Connection getConnection() {
        if(dbConnect == null) {
            try {
                return dbConnect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            } catch (SQLException e) {
                log.log(Level.SEVERE, "Creation connection with DB failed",e);
                e.printStackTrace();
            }
        }
        return dbConnect;
    }


}
