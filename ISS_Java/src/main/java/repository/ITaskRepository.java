package repository;

import model.Task;
import model.User;

public interface ITaskRepository extends Repository<Long, Task> {
    Iterable<Task> getEmployeeTasks(User employee);
}
