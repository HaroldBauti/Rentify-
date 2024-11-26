package com.univerisity.Domain.models;

import java.util.ArrayList;

public class User {
    private int idRauser;
    private String nombre;
    private String apellidos;
    private String email;
    private String clave;
    private String celular;
    private boolean tipo;

    public User() { }

    public User(int idRauser, String nombre, String apellidos, String email, String clave, String celular) {
        this.idRauser = idRauser;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.clave = clave;
        this.celular = celular;
    }

    public User(int idRauser, String nombre, String apellidos, String email, String clave, String celular, boolean tipo) {
        this.idRauser = idRauser;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.clave = clave;
        this.celular = celular;
        this.tipo = tipo;
    }

    public int getIdRauser() {
        return idRauser;
    }

    public void setIdRauser(int idRauser) {
        this.idRauser = idRauser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "User{" +
                "idRauser=" + idRauser +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", clave='" + clave + '\'' +
                ", celular='" + celular + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
