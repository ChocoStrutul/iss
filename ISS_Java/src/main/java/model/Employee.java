package model;

public class Employee extends User {

    private String name;

    public Employee(Long uid, String email, String password, UserType type, String name) {
        super(uid, email, password, type, name);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
