package repository;

import exceptions.RepoException;
import model.Boss;
import model.Employee;
import model.User;
import model.UserType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserRepositoryHibernate implements IUserRepository {

    private SessionFactory sessionFactory;

    public UserRepositoryHibernate(Properties properties, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getOne(Long id) {
        return null;
    }

    @Override
    public Iterable<User> getAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User login(String email, String password) {

        User user = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user = session.createQuery("from User where email = '" + email + "' and password = '" + password + "'", User.class)
                        .getSingleResult();

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        if (user == null) {
            throw new RepoException("Invalid email or password!");
        }
        return user;
    }
}

