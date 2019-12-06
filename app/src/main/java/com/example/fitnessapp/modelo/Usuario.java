package com.example.fitnessapp.modelo;

public class Usuario {

    private String dni;
    private String contrasena;

    public Usuario(String dni, String contrasena) {
        this.dni = dni;
        this.contrasena = contrasena;
    }

    public Usuario() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
