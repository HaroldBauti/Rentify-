package com.univerisity.Domain.models;

import com.univerisity.DataAccess.DAOs.DAOImages;
import com.univerisity.DataAccess.DAOs.DAOUser;
import com.univerisity.Domain.services.ImageService;
import com.univerisity.Domain.services.UserService;

import java.util.ArrayList;

public class Property {

    private int idRaproperty;
    private String titulo;
    private String descripcion;
    private String direccion;
    private String city;
    private double longitud;
    private double latitud;
    private double precio;
    private boolean estado;
    private int idUser;
    private int idUserNavigation;
    private ArrayList<Image> raimagenes;

    public Property() {}

    public Property(int idRaproperty, String titulo, String descripcion, String direccion, String city, double longitud, double latitud, double precio) {
        this.idRaproperty = idRaproperty;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.city = city;
        this.longitud = longitud;
        this.latitud = latitud;
        this.precio = precio;
    }

    public Property(int idRaproperty, String titulo, String descripcion, String direccion, String city, double longitud, double latitud, double precio, boolean estado, int idUser, int idUserNavigation, ArrayList<Image> raimagenes) {
        this.idRaproperty = idRaproperty;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.city = city;
        this.longitud = longitud;
        this.latitud = latitud;
        this.precio = precio;
        this.estado = estado;
        this.idUser = idUser;
        this.idUserNavigation = idUserNavigation;
        this.raimagenes = raimagenes;
    }

    public int getIdRaproperty() {
        return idRaproperty;
    }

    public void setIdRaproperty(int idRaproperty) {
        this.idRaproperty = idRaproperty;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUserNavigation() {
        return idUserNavigation;
    }

    public void setIdUserNavigation(int idUserNavigation) {
        this.idUserNavigation = idUserNavigation;
    }

    public ArrayList<Image> getRaimagenes() {
        return raimagenes;
    }

    public void setRaimagenes(ArrayList<Image> raimagenes) {
        this.raimagenes = raimagenes;
    }

    @Override
    public String toString() {
        return "Property{" +
                "idRaproperty=" + idRaproperty +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", city='" + city + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                ", precio=" + precio +
                ", estado=" + estado +
                ", idUser=" + idUser +
                ", idUserNavigation=" + idUserNavigation +
                ", raimagenes=" + raimagenes +
                '}';
    }

    //Metodos
    public void obtenerPropietario(DAOUser.UserCallback callback){
        if(idUser != 0){
            UserService userService = new UserService();
            userService.getUser(getIdUser(), callback);
        }
        else{
            callback.onLoginFailure("Proptario no encontrado");
        }
    }

    public void obtenerImagenes(DAOImages.ImageListCallback callback){
        if(idRaproperty != 0){
            ImageService service = new ImageService();
            service.getImages(idRaproperty, callback);
        }
        else{
            callback.onFailure("Propietario no encontrado");
        }
    }
}
