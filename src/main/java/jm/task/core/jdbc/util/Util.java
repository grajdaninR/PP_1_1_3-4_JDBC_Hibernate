package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

//в настройках подключения отключи автокоммит и в каждом методе сделай коммит и ролбэк
public class Util {
    private static Logger log = Logger.getLogger(Util.class.getName());

    private static final String DB_URL = "jdbc:mysql://localhost:3306/usersdb?serverTimezone=Europe/Moscow";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "9219";
    private static final String Dialect_SQL = "org.hibernate.dialect.MySQL8Dialect";

    private static Connection dbConnect;
    private static SessionFactory factory;

    private Util() {}

    public static  Connection getConnection() {
        if(dbConnect == null) {
            try {
                if (dbConnect != null) {
                    dbConnect.setAutoCommit(false);
                }
                return dbConnect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            } catch (SQLException e) {
                log.log(Level.SEVERE, "Creation connection with DB failed",e);
                return null;
            } finally {
                try {
                    dbConnect.setAutoCommit(false);
                } catch (SQLException e) {
                    log.log(Level.SEVERE,"Setting setAutoCommit(false) is field", e);
                }
            }
        }
        return dbConnect;
    }

    public static SessionFactory getSessionFactory() {
        if (factory == null)
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.URL,DB_URL);
                settings.put(Environment.USER,DB_USER);
                settings.put(Environment.PASS,DB_PASS);
                settings.put(Environment.DIALECT,Dialect_SQL);
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                factory = configuration.buildSessionFactory(serviceRegistry);

            }catch (Throwable ex) {
                System.err.println("Failed to create sessionFactory object." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        return factory;
    }
}
