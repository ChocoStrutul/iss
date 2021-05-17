package repository;

import model.Employee;
import model.Log;
import model.UserType;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LogRepository implements Repository<Long, Log> {

    private JdbcUtils dbUtils;

    public LogRepository(Properties properties) {
        dbUtils=new JdbcUtils(properties);
    }

    @Override
    public Log getOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Log> getAll() {


        Connection connection = dbUtils.getConnection();
        List<Log> logs = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM logs")) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {

                Long id = resultSet.getLong("id");
                String arrivalTime = resultSet.getString("arrivalTime");
                Long uid = resultSet.getLong("uid");
                Employee employee = null;

                try(PreparedStatement userStatement = connection.prepareStatement("SELECT * FROM users where id = ?")) {
                    userStatement.setLong(1, uid);
                    ResultSet usersResultSet = userStatement.executeQuery();

                    if(usersResultSet.next()) {

                        String email = usersResultSet.getString("email");
                        String password = usersResultSet.getString("password");
                        UserType type = UserType.EMPLOYEE;
                        String name = usersResultSet.getString("name");
                        employee = new Employee(id, email, password, type, name);
                    }

                } catch (SQLException ex) {
                    System.err.println("DB Error " + ex);
                }

                Log log = new Log(id, arrivalTime, employee);
                logs.add(log);

            }

        } catch (SQLException ex) {
            System.err.println("DB Error " + ex);
        }

        return logs;

    }

    @Override
    public Log save(Log entity) {
        return null;
    }

    @Override
    public Log delete(Long aLong) {
        return null;
    }

    @Override
    public Log update(Log entity) {
        return null;
    }
}
