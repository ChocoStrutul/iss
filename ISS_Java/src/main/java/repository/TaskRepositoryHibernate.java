package repository;

import model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class TaskRepositoryHibernate implements Repository<Long, Task> {

    private SessionFactory sessionFactory;

    public TaskRepositoryHibernate(Properties properties, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Task getOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Task> getAll() {
        return null;
    }

    @Override
    public Task save(Task task) {

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(task);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return task;
    }

    @Override
    public Task delete(Long aLong) {
        return null;
    }

    @Override
    public Task update(Task entity) {
        return null;
    }
}
