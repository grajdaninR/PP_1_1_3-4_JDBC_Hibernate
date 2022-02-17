package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args)  {
        UserDaoJDBCImpl userD = new UserDaoJDBCImpl();
        userD.createUsersTable();

        userD.saveUser("Cloud","Strife", (byte) 25);
        userD.saveUser("Tifa","Lochart", (byte) 24);
        userD.saveUser("Squall","Leonhart", (byte) 120);
        userD.saveUser("Rinoa","Heartilly", (byte) 100);

        userD.getAllUsers();
        userD.cleanUsersTable();
        userD.dropUsersTable();
    }
}
