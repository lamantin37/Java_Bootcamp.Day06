package edu.school21.models;

import java.util.Objects;

public class User {
    public static final String ENTITY_TABLE_NAME = "user";
    private Long id;
    private String login;
    private String password;
    private Boolean authenticationStatus;

    public User(Long id, String login, String password, Boolean authenticationStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authenticationStatus = authenticationStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Boolean getAuthenticationStatus() { return authenticationStatus; }
    public void setAuthenticationStatus(Boolean authenticationStatus) { this.authenticationStatus = authenticationStatus; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(login, other.login) &&
                Objects.equals(password, other.password) &&
                Objects.equals(authenticationStatus, other.authenticationStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, authenticationStatus);
    }

    @Override
    public String toString() {
        return "USER : {\n   id = " + id + ",\n   login = \"" + login + "\",\n   password = \"" +
                password + "\",\n   authentication status = " +
                authenticationStatus + "\n}";
    }
}
