package pl.info.czerniak.library.dao;

import pl.info.czerniak.library.model.User;
import pl.info.czerniak.library.util.ConnectionProvider;
import pl.info.czerniak.library.util.DbOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresqlUserDAO implements UserDAO{
    private final static String CREATE = "INSERT INTO users(pesel, firstName, lastName) VALUES(?,?,?);";
    private final static String READ = "SELECT pesel, firstName, lastName FROM users WHERE pesel = ?;";
    private final static String UPDATE = "UPDATE users SET = pesel=?, lastName=?, firstName=? WHERE pesel = ?;";
    private final static String DELETE = "DELETE FROM users WHERE pesel=?;";

    @Override
    public void create(User user) {
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);){
            preparedStatement.setString(1,user.getPesel());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DbOperationException(e);
        }
    }

    @Override
    public User read(String pesel) {
        User resultUser = null;
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ);){
            preparedStatement.setString(1,pesel);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                resultUser = new User();
                resultUser.setPesel(resultSet.getString("pesel"));
                resultUser.setFirstName(resultSet.getString("firstName"));
                resultUser.setLastName(resultSet.getString("lastName"));
            }
        } catch (SQLException e){
            throw new DbOperationException(e);
        }
        return resultUser;
    }

    @Override
    public void update(User user) {
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);){
            preparedStatement.setString(1,user.getPesel());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPesel());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DbOperationException(e);
        }
    }

    @Override
    public void delete(User user) {
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);){
            preparedStatement.setString(1,user.getPesel());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DbOperationException(e);
        }
    }
}
