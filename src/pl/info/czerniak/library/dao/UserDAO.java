package pl.info.czerniak.library.dao;

import pl.info.czerniak.library.model.User;

public interface UserDAO {
    public void create(User user);
    public User read(String pesel);
    public void update(User user);
    public void delete(User user);
}
