package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoHibernateImpl;


public class Main {
    public static void main(String[] args)  {
        UserDaoHibernateImpl userD = new UserDaoHibernateImpl();
        userD.createUsersTable();

        userD.saveUser("Cloud","Strife", (byte) 25);
        userD.saveUser("Tifa","Lochart", (byte) 24);
        userD.saveUser("Squall","Leonhart", (byte) 120);
        userD.saveUser("Rinoa","Heartilly", (byte) 100);

        System.out.println();

        userD.cleanUsersTable();
        userD.dropUsersTable();
    }
}
