package repository;

import model.Boss;
import model.Employee;
import model.User;
import model.UserType;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserRepository implements IUserRepository {

    private JdbcUtils dbUtils;

    public UserRepository(Properties properties) {
        dbUtils=new JdbcUtils(properties);
    }

    @Override
    public User getOne(Long id) {

        Connection connection = dbUtils.getConnection();
        User user = null;

        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {

                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String typeString = resultSet.getString("type");
                UserType type;
                if (typeString.equals("BOSS")) {
                    type = UserType.BOSS;
                    user = new Boss(id, email, password, type);
                }
                else {
                    type = UserType.EMPLOYEE;
                    String name = resultSet.getString("name");
                    user = new Employee(id, email, password, type, name);
                }
            }

        } catch (SQLException ex) {
            System.err.println("DB Error " + ex);
        }

        return user;
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
        Connection connection = dbUtils.getConnection();
        User user = null;

        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? and password = ?")) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {

                Long id = resultSet.getLong("id");
                String typeString = resultSet.getString("type");
                UserType type;
                if (typeString.equals("boss")) {
                    type = UserType.BOSS;
                    user = new Boss(id, email, password, type);
                }
                else {
                    type = UserType.EMPLOYEE;
                    String name = resultSet.getString("name");
                    user = new Employee(id, email, password, type, name);
                }
            }

        } catch (SQLException ex) {
            System.err.println("DB Error " + ex);
        }

        return user;
    }
}
