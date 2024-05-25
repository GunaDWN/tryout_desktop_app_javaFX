package com.example.tryout_kl1.Models;

public class User {
    private int idUser;
    private String username;
    private String password;
    private Role role;

    public User(int idUser, String username, String password, Role role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public enum Role{
        ADMIN,USER
    }
}
