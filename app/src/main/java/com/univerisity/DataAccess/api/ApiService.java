package com.univerisity.DataAccess.api;

import java.util.ArrayList;
import java.util.List;

import com.univerisity.Domain.models.Image;
import com.univerisity.Domain.models.Login;
import com.univerisity.Domain.models.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    @Headers("Content-type: application/json")
    @POST("/Login")
    Call<Login> login(@Query("Correo") String correo, @Query("Clave") String clave);

    @Headers("Content-type: application/json")
    @POST("/RegisterUser")
    Call<Response> register(@Query("Nombre") String nombre, @Query("Apellidos") String apellido, @Query("Celular") String celular, @Query("Email") String correo, @Query("Clave") String clave);

    @Headers("Content-type: application/json")
    @GET("/SearchEmail")
    Call<Login> getEmail(@Query("objeto") String email);

    @Headers("Content-type: application/json")
    @PUT("/UpdatePassword")
    Call<Response> changePassword(@Query("password") String password, @Query("id") int idUser);

    @Headers("Content-type: application/json")
    @POST("/RegisterProperty")
    Call<Response> registrarPropiedad(@Query("IdUser") int idUser, @Query("Titulo") String titulo, @Query("Descripcion") String descripcion, @Query("Direccion") String direccion, @Query("City") String ciudad, @Query("Longitud") double longitud, @Query("Latitud") double latitud, @Query("Precio") double precio);

    @Headers("Content-type: application/json")
    @GET("/Propertieslist")
    Call<Response> listarPropiedades();

    @Headers("Content-type: application/json")
    @PUT("/UpdateProperty")
    Call<Response> actualizarPropiedad(@Query("Id") int id, @Query("Titulo") String titulo, @Query("Descripcion") String descripcion, @Query("Direccion") String direccion, @Query("City") String ciudad, @Query("Longitud") double longitud, @Query("Latitud") double latitud, @Query("Precio") double precio);

    @Headers("Content-type: application/json")
    @PUT("/RentProperty")
    Call<Response> rentarPropiedad(@Query("id") int id);

    @Headers("Content-type: application/json")
    @GET("/MyPropertieslist")
    Call<Response> MyProperties(@Query("idUser") int id);

    @Headers("Content-type: application/json")
    @GET("/PropertiesSearch")
    Call<Response> PropertiesSearch(@Query("search") String search);

    @Headers("Content-type: application/json")
    @PUT("/ChangeMode")
    Call<Response> cambiarModo(@Query("id") int id);

    @Headers("Content-type: application/json")
    @GET("/DataUser")
    Call<Response> infoUsuario(@Query("id") int id);

    @Headers("Content-type: application/json")
    @PUT("/UpdateUser")
    Call<Response> updateUser(@Query("Id") int id, @Query("Nombre") String nombre, @Query("Apellidos") String apellido, @Query("Celular") String celular, @Query("Email") String correo, @Query("Clave") String clave);

    @Headers("Content-type: application/json")
    @GET("/Favoriteslist")
    Call<Response> favoritiesList(@Query("idUser") int idUser);

    @Headers("Content-type: application/json")
    @POST("/addfavorites")
    Call<Response> addFavorite(@Query("IdUsuario") int idUser, @Query("IdPropiedad") int idProperty);

    @Headers("Content-type: application/json")
    @DELETE("/Deletefavorites")
    Call<Response> deleteFavorite(@Query("idUser") int idUser, @Query("IdPropiedad") int idProperty);

    @Headers("Content-type: application/json")
    @GET("/ImagesGet")
    Call<Response> getImages(@Query("idProperty") int idProperty);

    @Headers("Content-type: application/json")
    @POST("/ImagesProperty")
    Call<Response> saveImages(@Query("IdPropiedad") int idPropiedad, @Query("ImagenUrl") String url, @Query("Titulo") String titulo);
}
