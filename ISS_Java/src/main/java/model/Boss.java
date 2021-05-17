package model;

public class Boss extends User {
    public Boss(Long uid, String email, String password, UserType type) {
        super(uid, email, password, type, null);
    }
}
