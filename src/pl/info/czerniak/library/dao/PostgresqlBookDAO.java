package pl.info.czerniak.library.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import pl.info.czerniak.library.model.Book;
import pl.info.czerniak.library.util.ConnectionProvider;

import java.util.List;

public class PostgresqlBookDAO implements BookDAO{
    private final static String CREATE = "INSERT INTO book(isbn,title,description) VALUES(:isbn, :title, :description);";
    private final static String READ = "SELECT isbn,title,description FROM book WHERE isbn = :isbn;";
    private final static String UPDATE = "UPDATE book SET isbn= :isbn, title= :title, description= :description WHERE isbn= :isbn;";
    private final static String DELETE = "DELETE FROM book WHERE isbn= :isbn;";

    private NamedParameterJdbcTemplate template;

    public PostgresqlBookDAO() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDSInstance());
    }

    public void create(Book book) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        template.update(CREATE, parameterSource);
    }

    public Book read(String isbn) {
        Book resultBook = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("isbn",isbn);
        List<Book> bookList = template.query(READ,parameterSource, BeanPropertyRowMapper.newInstance(Book.class));
        if(bookList.get(0) != null){
            resultBook = bookList.get(0);
        }
        return resultBook;
    }

    public void update(Book book){
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        template.update(UPDATE,parameterSource);
    }

    public void delete(Book book){
        SqlParameterSource parameterSource = new MapSqlParameterSource("isbn", book.getIsbn());
        template.update(DELETE,parameterSource);
    }

}
