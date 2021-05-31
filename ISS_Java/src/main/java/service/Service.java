package service;

import model.Log;
import model.Task;
import model.User;
import repository.ILogRepository;
import repository.ITaskRepository;
import repository.IUserRepository;
import repository.Repository;
import utils.observer.EventType;
import utils.observer.Observable;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Service implements Observable {

    private IUserRepository userRepository;
    private ILogRepository logRepository;
    private ITaskRepository taskRepository;

    private List<Observer> observers=new ArrayList<>();

    public Service(IUserRepository userRepository, ILogRepository logRepository, ITaskRepository taskRepository) {

        this.userRepository = userRepository;
        this.logRepository = logRepository;
        this.taskRepository = taskRepository;

    }

    public User login(String email, String password) {
        return userRepository.login(email, password);
    }

    public Log addLog(Log log) {

        log = logRepository.save(log);
        notifyObservers(EventType.LOGIN);
        return log;

    }

    public Iterable<Log> getLogs() {
        return logRepository.getAll();
    }

    public Task sendTask(Task task) {

        task = taskRepository.save(task);
        notifyObservers(EventType.SEND_TASK);
        return task;

    }

    public Task postTaskOnFeed(Task task) {

        task = taskRepository.save(task);
        notifyObservers(EventType.POST_ON_FEED);
        return task;

    }

    public Task acceptFeedTask(Task task) {

        task = taskRepository.update(task);
        notifyObservers(EventType.ACCEPT_FEED_TASK);
        return task;

    }

    public Iterable<Task> getEmployeeTasks(User employee){
        return taskRepository.getEmployeeTasks(employee);
    }

    public Iterable<Task> getFeedTasks(){
        return taskRepository.getAll();
    }

    public void logout(User user) {

        logRepository.logout(user);
        notifyObservers(EventType.LOGOUT);

    }

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EventType t) {
        observers.stream().forEach(x->x.update(t));
    }

}
