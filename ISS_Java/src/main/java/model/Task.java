package model;

public class Task extends Entity<Long> {

    private String description;
    private TaskType type;
    private User employee;
    private String typeString;

    private Long uid;

    public Task(Long id, String description, TaskType type, User employee) {
        setId(id);
        this.description = description;
        this.type = type;
        this.employee = employee;
        this.typeString = type.toString();
        this.uid = employee.getId();
    }

    public Task(String description, TaskType type, User employee) {
        this.description = description;
        this.type = type;
        this.employee = employee;
        this.typeString = type.toString();
        this.uid = employee.getId();
    }

    public Task() {
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

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
        if(typeString.equals("PRIVATE"))
            this.type = TaskType.PRIVATE;
        else
            this.type = TaskType.PUBLIC;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
