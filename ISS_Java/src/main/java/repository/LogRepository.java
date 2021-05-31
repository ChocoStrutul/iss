package repository;

import exceptions.RepoException;
import model.Log;
import model.Task;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Properties;

public class LogRepository implements ILogRepository {

    private SessionFactory sessionFactory;

    public LogRepository(Properties properties, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Log getOne(Long uid) {
        Log log = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                log = session.createQuery("from Log where uid = " + uid, Log.class).getSingleResult();

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return log;
    }

    @Override
    public Iterable<Log> getAll() {
        List<Log> logs = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                logs =
                        session.createQuery("from Log", Log.class).list();
                tx.commit();

                logs.forEach(log -> {

                    User user = session.createQuery("from User where id = " + log.getUid(), User.class).getSingleResult();
                    log.setEmployee(user);

                });

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return logs;
    }

    @Override
    public Log save(Log log) {

        if(getOne(log.getUid()) != null)
            throw new RepoException("User already logged in!");

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(log);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return log;
    }

    @Override
    public Log delete(Long aLong) {
        return null;
    }

    @Override
    public Log update(Log entity) {
        return null;
    }

    @Override
    public void logout(User employee) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Log log = session.createQuery("from Log where uid = " + employee.getId(), Log.class)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(log);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }
}
