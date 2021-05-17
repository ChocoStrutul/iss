package service;

import model.Log;
import model.Task;
import model.User;
import repository.IUserRepository;
import repository.Repository;

public class Service {

    IUserRepository userRepository;
    Repository<Long, Log> logRepository;
    Repository<Long, Task> taskRepository;

    public Service(IUserRepository userRepository, Repository<Long, Log> logRepository, Repository<Long, Task> taskRepository) {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
        this.taskRepository = taskRepository;
    }

    public User login(String email, String password) {
        return userRepository.login(email, password);
    }

    public void addLog(Log log) {

    }

    public Iterable<Log> getLogs() {
        return logRepository.getAll();
    }

    public Task sendTask(Task task) {
        return taskRepository.save(task);
    }

}
