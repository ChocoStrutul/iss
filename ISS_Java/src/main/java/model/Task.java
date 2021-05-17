package model;

public class Task extends Entity<Long> {

    private String description;
    private TaskType type;
    private Employee employee;

    public Task(Long id, String description, TaskType type, Employee employee) {
        setId(id);
        this.description = description;
        this.type = type;
        this.employee = employee;
    }

    public Task(String description, TaskType type, Employee employee) {
        this.description = description;
        this.type = type;
        this.employee = employee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
