package model;

import java.time.LocalDateTime;

public class Log extends Entity<Long> {

    private String arrivalTime;
    private User employee;

    public Log(Long id, String arrivalTime, User employee) {
        setId(id);
        this.arrivalTime = arrivalTime;
        this.employee = employee;
    }

    public Log(String arrivalTime, User employee) {
        this.arrivalTime = arrivalTime;
        this.employee = employee;
    }

    public Log(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getEmployeeName() {
        return employee.getName();
    }
}
