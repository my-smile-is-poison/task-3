package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `data_base`.`new_table` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement() ) {
            statement.executeUpdate("DROP TABLE IF EXISTS new_table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO new_table (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошел анлак и пользователь не был создан");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM new_table WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM new_table");
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM new_table");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
