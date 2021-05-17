package model;

import java.time.LocalDateTime;

public class Log extends Entity<Long> {

    private String arrivalTime;
    private Employee employee;

    public Log(Long id, String arrivalTime, Employee employee) {
        setId(id);
        this.arrivalTime = arrivalTime;
        this.employee = employee;
    }

    public Log(String arrivalTime, Employee employee) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getEmployeeName() {
        return employee.getName();
    }
}
