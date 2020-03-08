package pl.info.czerniak.library.dao;

import pl.info.czerniak.library.model.Book;
import pl.info.czerniak.library.util.ConnectionProvider;
import pl.info.czerniak.library.util.DbOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresqlBookDAO implements BookDAO{
    private final static String CREATE = "INSERT INTO book(isbn,title,description) VALUES(?,?,?);";
    private final static String READ = "SELECT isbn,title,description FROM book WHERE isbn =?;";
    private final static String UPDATE = "UPDATE book SET isbn=?, title=?, description=? WHERE isbn=?;";
    private final static String DELETE = "DELETE FROM book WHERE isbn=?;";

    public void create(Book book) {
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);){
            preparedStatement.setString(1,book.getIsbn());
            preparedStatement.setString(2,book.getTitle());
            preparedStatement.setString(3,book.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbOperationException(e);
        }
    }

    public Book read(String isbn) {
        Book resultBook = null;
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ);){
            preparedStatement.setString(1,isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                resultBook = new Book();
                resultBook.setIsbn(resultSet.getString("isbn"));
                resultBook.setTitle(resultSet.getString("title"));
                resultBook.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            throw new DbOperationException(e);
        }
        return resultBook;
    }

    public void update(Book book){
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);){
            preparedStatement.setString(1,book.getIsbn());
            preparedStatement.setString(2,book.getTitle());
            preparedStatement.setString(3,book.getDescription());
            preparedStatement.setString(4,book.getIsbn());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DbOperationException(e);
        }
    }

    public void delete(Book book){
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);){
            preparedStatement.setString(1,book.getIsbn());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DbOperationException(e);
        }
    }

}