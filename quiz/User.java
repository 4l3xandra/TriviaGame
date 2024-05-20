package quiz;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // register a new user
    public static User register(String username, String password) {
        return new User(username, password);
    }

    // authenticate user
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}