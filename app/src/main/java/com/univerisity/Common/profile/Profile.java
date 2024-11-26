package com.univerisity.Common.profile;

import com.univerisity.DataAccess.DAOs.DAOProperty;
import com.univerisity.Domain.models.Favorito;
import com.univerisity.Domain.services.PropertyService;

import java.util.ArrayList;

public class Profile {
    private static int idRauser;
    private static String nombre;
    private static String apellidos;
    private static String email;
    private static String clave;
    private static String celular;
    private static boolean tipo;

    public Profile() {}

    public static int getIdRauser() {
        return idRauser;
    }

    public static void setIdRauser(int idRauser) {
        Profile.idRauser = idRauser;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        Profile.nombre = nombre;
    }

    public static String getApellidos() {
        return apellidos;
    }

    public static void setApellidos(String apellidos) {
        Profile.apellidos = apellidos;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Profile.email = email;
    }

    public static String getClave() {
        return clave;
    }

    public static void setClave(String clave) {
        Profile.clave = clave;
    }

    public static String getCelular() {
        return celular;
    }

    public static void setCelular(String celular) {
        Profile.celular = celular;
    }

    public static boolean isTipo() {
        return tipo;
    }

    public static void setTipo(boolean tipo) {
        Profile.tipo = tipo;
    }

    public static void getFavorites(DAOProperty.FavoriteCallback callback){
        PropertyService service = new PropertyService();
        service.getFavorities(idRauser, new DAOProperty.FavoriteCallback() {
            @Override
            public void onSuccess(ArrayList<Favorito> favoriteLost) {
                callback.onSuccess(favoriteLost);
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }
}
