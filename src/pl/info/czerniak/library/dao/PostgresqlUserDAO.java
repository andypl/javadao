package pl.info.czerniak.library.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import pl.info.czerniak.library.model.User;
import pl.info.czerniak.library.util.ConnectionProvider;

import java.util.List;

public class PostgresqlUserDAO implements UserDAO{
    private final static String CREATE = "INSERT INTO users(pesel, firstName, lastName) VALUES(:pesel, :firstName, :lastName);";
    private final static String READ = "SELECT pesel, firstName, lastName FROM users WHERE pesel = ?;";
    private final static String UPDATE = "UPDATE users SET pesel= :pesel, lastName= :lastName, firstName= :firstName WHERE pesel = :pesel;";
    private final static String DELETE = "DELETE FROM users WHERE pesel=?;";

    private NamedParameterJdbcTemplate template;

    public PostgresqlUserDAO() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDSInstance());
    }

    @Override
    public void create(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        template.update(CREATE,parameterSource);
    }

    @Override
    public User read(String pesel) {
        User resultUser = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("pesel", pesel);
        List<User> userList = template.query(READ, parameterSource, BeanPropertyRowMapper.newInstance(User.class));
        if(userList.get(0) != null){
            resultUser = userList.get(0);
        }
        return resultUser;
    }

    @Override
    public void update(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        template.update(UPDATE,parameterSource);
    }

    @Override
    public void delete(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        template.update(DELETE, parameterSource);
    }
}
