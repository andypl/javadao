package pl.info.czerniak.library.dao;

import pl.info.czerniak.library.model.Book;

public class PostgresqlDaoFactory extends DAOFactory {

    @Override
    public BookDAO getBookDAO() {
        return new PostgresqlBookDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new PostgresqlUserDAO();
    }
}
