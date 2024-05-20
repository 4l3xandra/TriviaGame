package quiz;

import java.util.Map;

public class Login {
    private Map<String, String> users;

    public Login(Map<String, String> users) {
        this.users = users;
    }

    // register a new user
    public void register(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, password);
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists. Please choose another one.");
        }
    }

    // authenticate a user
    public boolean authenticate(String username, String password) {
        System.out.println("Attempting to authenticate with username: " + username + " and password: " + password);

        if (users.containsKey(username)) {
            System.out.println("Username found.");
            String storedPassword = users.get(username);
            System.out.println("Stored password for username " + username + ": " + storedPassword);

            if (storedPassword.equals(password)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("Username not found in users map.");
        }

        System.out.println("Invalid username or password.");
        return false;
    }
}