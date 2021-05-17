package repository;

import model.User;

public interface IUserRepository extends Repository<Long, User> {
    public User login(String email, String password);
}
