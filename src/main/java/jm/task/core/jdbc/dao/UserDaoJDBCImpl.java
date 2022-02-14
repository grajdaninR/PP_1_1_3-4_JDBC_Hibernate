package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    private static Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement state = connection.createStatement()) {
            state.execute("CREATE TABLE IF NOT EXISTS  users(id integer not null primary key unique auto_increment," +
                    " name varchar(50), lastName varchar(50), age smallint) CHARACTER SET = utf8 , COLLATE = utf8_general_ci");
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Creation of DB is failed", e);
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement state = connection.createStatement()) {
            state.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e){
            log.log(Level.SEVERE,"Dropping of DB is filed", e);
            e.printStackTrace();
        }


    }

    public void saveUser(String name, String lastName, byte age) {
    try (PreparedStatement PreState = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?,?,?)");
        PreparedStatement PreState1 = connection.prepareStatement("SELECT name, lastName FROM users WHERE name = ? and lastName = ? ")) {

        PreState.setString(1,name);
        PreState.setString(2,lastName);
        PreState.setByte(3,age);
        PreState.executeUpdate();

        PreState1.setString(1,name);
        PreState1.setString(2,lastName);
        ResultSet reSet = PreState1.executeQuery();
        while (reSet.next()){
            String usName = reSet.getString("name");
            String usLastName = reSet.getString("lastName");
            System.out.println("User с именем "+ usName + " "+ usLastName + " добавлен в базу");
        }

    } catch (SQLException e){
        log.log(Level.SEVERE,"Saving of user to DB is filed",e);
      }
    }



    public void removeUserById(long id) {
        try (PreparedStatement PreState = connection.prepareStatement("DELETE FROM users WHERE id =?")) {
            PreState.setInt(1, (int) id);
            PreState.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Remove of user to DB is filed");
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Statement state = connection.createStatement()) {
            ResultSet reSet = state.executeQuery("SELECT * FROM users");

            while (reSet.next()) {
            Long id = reSet.getLong("id");
            String name = reSet.getString("name");
            String lastName = reSet.getString("lastName");
            Byte age = reSet.getByte("age");

            allUsers.add(new User(id,name,lastName,age));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Getting of all users from DB is filed");
            e.printStackTrace();
        }

        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement state = connection.createStatement()) {
            state.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Cleaning of DB \"users\" is filed",e);
            e.printStackTrace();
        }

    }
}
