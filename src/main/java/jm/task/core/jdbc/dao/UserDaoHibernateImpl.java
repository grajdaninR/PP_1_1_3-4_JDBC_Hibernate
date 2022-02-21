package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory factory = Util.getSessionFactory();
    private static Logger log = Logger.getLogger(UserDaoHibernateImpl.class.getName());


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction ts = null;

        try(Session session = factory.openSession()) {
            ts = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS  users(id integer not null primary key unique auto_increment," +
                    " name varchar(50), lastName varchar(50), age smallint) CHARACTER SET = utf8 , COLLATE = utf8_general_ci");
            query.executeUpdate();
            ts.commit();

        } catch (Exception e) {
            log.log(Level.SEVERE,"Creation of DB is failed...Transaction is being rolled back", e);
            if (ts!=null) {
                ts.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction ts = null;

        try(Session session = factory.openSession()) {
            ts = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users");
            query.executeUpdate();
            ts.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE,"Dropping of DB is filed...Transaction is being rolled back", e);
            if (ts != null) {
                ts.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction ts = null;

        try(Session session = factory.openSession()) {
            ts = session.beginTransaction();
            session.save(new User(name, lastName, age));
            ts.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE,"Saving of user to DB is filed...Transaction is being rolled back", e);
            if (ts != null) {
                ts.rollback();
            }
        }
        System.out.println("User с именем " + name + " добавлен в базу");
    }

    @Override
    public void removeUserById(long id) {
        Transaction ts = null;

        try(Session session = factory.openSession()) {
            ts = session.beginTransaction();
            User user = session.get(User.class, id);
            if (session != null){
                session.delete(user);
            }
            ts.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE,"Remove of user to DB is filed...Transaction is being rolled back", e);
            if (ts != null) {
                ts.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction ts = null;
        List<User> allUsers = null;

        try(Session session = factory.openSession()) {
            ts = session.beginTransaction();

            Query<User> query = session.createQuery(" SELECT u FROM User u", User.class);
            allUsers = query.getResultList();
            ts.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Getting of all users from DB is filed...Transaction is being rolled back",e);
            if (ts != null){
                ts.rollback();
                e.printStackTrace();
            }
        }

        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Transaction ts = null;

        try(Session session = factory.openSession()) {
            ts = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            ts.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE,"Cleaning of DB users is filed...Transaction is being rolled back", e);
            if (ts != null) {
                ts.rollback();
            }
        }

    }
}
