package repository;

import model.Log;
import model.User;

public interface ILogRepository extends Repository<Long, Log> {
    void logout(User employee);
}
