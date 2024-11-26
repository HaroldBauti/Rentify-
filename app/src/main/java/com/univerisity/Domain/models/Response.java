package com.univerisity.Domain.models;

import java.util.List;

public class Response {

    //General
    private boolean isSuccess;

    private boolean isSucces;

    private int idProperty;

    //Login
    private String token;

    //Properties
    private List<Property> value;

    //Faroties
    private List<Favorito> valueFavoritos;

    //Busquedas
    private List<Property> valueSearch;

    //Iamges
    private List<Image> valueImagenes;

    //User
    private User usuario;

    public Response(boolean isSuccess, String token, List<Property> value, User usuario, List<Favorito> valueFavoritos, List<Image> valueImagenes, List<Property> valueSearch) {
        this.isSuccess = isSuccess;
        this.token = token;
        this.value = value;
        this.usuario = usuario;
        this.valueFavoritos = valueFavoritos;
        this.valueSearch = valueSearch;
        this.valueImagenes = valueImagenes;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Property> getValue() {
        return value;
    }

    public void setValue(List<Property> value) {
        this.value = value;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public List<Favorito> getValueFavoritos() {
        return valueFavoritos;
    }

    public void setValueFavoritos(List<Favorito> valueFavoritos) {
        this.valueFavoritos = valueFavoritos;
    }

    public List<Property> getValueSearch() {
        return valueSearch;
    }

    public void setValueSearch(List<Property> valueSearch) {
        this.valueSearch = valueSearch;
    }

    public boolean isSucces() {
        return isSucces;
    }

    public void setSucces(boolean succes) {
        isSucces = succes;
    }

    public int getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(int idProperty) {
        this.idProperty = idProperty;
    }

    public List<Image> getValueImagenes() {
        return valueImagenes;
    }

    public void setValueImagenes(List<Image> valueImagenes) {
        this.valueImagenes = valueImagenes;
    }
}
