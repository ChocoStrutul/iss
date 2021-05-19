package model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class User extends Entity<Long>{

    private String email;
    private String password;
    private UserType type;
    private String name = null;
    private String typeString;

    public User(Long uid, String email, String password, UserType type, String name) {
        setId(uid);
        this.email = email;
        this.password = password;
        this.type = type;
        this.name = name;
        this.typeString = type.toString();
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
        if(typeString.equals("BOSS"))
            this.type = UserType.BOSS;
        else
            this.type = UserType.EMPLOYEE;
    }
}
