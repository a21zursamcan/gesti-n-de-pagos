package com.example.gestindegastos.login;

public class User {
    public String usuario;
    public String correo;
    public String pass;

    public User() {
    }

    public User(String usuario, String correo, String pass) {
        this.usuario = usuario;
        this.correo = correo;
        this.pass = pass;
    }
}
