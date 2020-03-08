package pl.info.czerniak.library.dao;

import pl.info.czerniak.library.model.Book;

public interface BookDAO {
    public void create(Book book);
    public Book read(String isbn);
    public void update(Book book);
    public void delete(Book book);
}
