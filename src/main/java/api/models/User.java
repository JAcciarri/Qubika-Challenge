package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a user in the Qubika Sports Club system.
 * Used for API request/response mapping via Jackson.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String[] roles;


    public User() {}

    public User(String email, String password, String firstName, String lastName, String... roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}