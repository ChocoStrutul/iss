package repository;

import model.Task;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class TaskRepository implements Repository<Long, Task> {

    private JdbcUtils dbUtils;

    public TaskRepository(Properties properties) {
        dbUtils=new JdbcUtils(properties);
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

        Connection connection = dbUtils.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (description, type, uid) VALUES(?,?,?)")) {
            statement.setString(1, task.getDescription());
            statement.setString(2, task.getType().toString());
            statement.setLong(3, task.getEmployee().getId());
            statement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("DB Error " + ex);
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
