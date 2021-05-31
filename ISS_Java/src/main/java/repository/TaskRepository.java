package repository;

import model.Task;
import model.TaskType;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TaskRepository implements ITaskRepository {

    private SessionFactory sessionFactory;

    public TaskRepository(Properties properties, SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;

    }

    @Override
    public Task getOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Task> getAll() {

        List<Task> tasks = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                tasks =
                        session.createQuery("from Task where typeString = 'PUBLIC'" , Task.class).list();
                tx.commit();

                tasks.forEach(task -> {

                    if(task.getUid() != null) {

                        User user = session.createQuery("from User where id = " + task.getUid(), User.class).getSingleResult();
                        task.setEmployee(user);

                    }

                });

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return tasks;

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
    public Task update(Task task) {

        Task foundTask = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{

                tx = session.beginTransaction();
                foundTask = session.load( Task.class, task.getId());
                foundTask.setUid(task.getUid());

                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }

        return foundTask;

    }

    @Override
    public Iterable<Task> getEmployeeTasks(User employee) {

        List<Task> tasks = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                tasks =
                        session.createQuery("from Task where uid = " + employee.getId(), Task.class).list();
                tx.commit();

                tasks.forEach(task -> {
                    task.setEmployee(employee);
                });

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return tasks;

    }

}
