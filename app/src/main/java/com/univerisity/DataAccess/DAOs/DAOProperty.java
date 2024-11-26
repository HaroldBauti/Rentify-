package com.univerisity.DataAccess.DAOs;

import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.api.ApiClient;
import com.univerisity.DataAccess.api.ApiService;
import com.univerisity.Domain.models.Favorito;
import com.univerisity.Domain.models.Property;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOProperty {
    private ApiService service;

    public DAOProperty() {
        this.service = ApiClient.getApiService();
    }

    // Callback para manejo de listas de propiedades
    public interface PropertyListCallback {
        void onSuccess(ArrayList<Property> properties);
        void onFailure(String errorMessage);
    }

    public interface GetProptertyCallback{
        void onSuccess(Property property);
        void onFailure(String errorMessage);
    }

    // Callback para operaciones individuales de propiedad
    public interface PropertyCallback {
        void onSuccess(int idProperty);
        void onFailure(String errorMessage);
    }

    // Callback para operaciones de favoritos
    public interface FavoriteCallback {
        void onSuccess(ArrayList<Favorito> favoriteLost);
        void onFailure(String errorMessage);
    }

    // Método para obtener todas las propiedades
    public void getAllProperties(PropertyListCallback callback) {
        Call<com.univerisity.Domain.models.Response> properties = service.listarPropiedades();
        properties.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Property> listProperties = (ArrayList<Property>) response.body().getValue();
                    callback.onSuccess(listProperties);
                } else {
                    callback.onFailure("No se obtuvieron los datos de las propiedades");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure("Error al obtener la lista de propiedades: " + t.getMessage());
            }
        });
    }

    public void getProperty(int id, GetProptertyCallback callback){
        Call<com.univerisity.Domain.models.Response> properties = service.listarPropiedades();
        properties.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Property> listProperties = (ArrayList<Property>) response.body().getValue();
                    for (Property p : listProperties){
                        if (p.getIdRaproperty() == id){
                            callback.onSuccess(p);
                            return;
                        }
                    }

                    callback.onFailure("No se encontro ninguan propiedad");
                } else {
                    callback.onFailure("No se obtuvieron los datos de las propiedades");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure("Error al obtener la lista de propiedades: " + t.getMessage());
            }
        });
    }

    // Método para guardar una propiedad
    public void saveProperty(int idUser, Property property, PropertyCallback callback) {
        Call<com.univerisity.Domain.models.Response> save = service.registrarPropiedad(idUser, property.getTitulo(), property.getDescripcion(),
                property.getDireccion(), property.getCity(), property.getLongitud(), property.getLatitud(), property.getPrecio());
        save.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getIdProperty());
                } else {
                    callback.onFailure("Error al registrar la propiedad");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure(t.getMessage() + t.getMessage());
            }
        });
    }

    // Método para editar una propiedad
    public void editProperty(int id, Property property, PropertyCallback callback) {
        Call<com.univerisity.Domain.models.Response> edit = service.actualizarPropiedad(id, property.getTitulo(), property.getDescripcion(),
                property.getDireccion(), property.getCity(), property.getLongitud(), property.getLatitud(), property.getPrecio());
        edit.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(id);
                } else {
                    callback.onFailure("No se pudo actualizar la propiedad");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure("Error al actualizar la propiedad: " + t.getMessage());
            }
        });
    }

    // Método para rentar una propiedad
    public void rentProperty(int id, PropertyCallback callback) {
        Call<com.univerisity.Domain.models.Response> rent = service.rentarPropiedad(id);
        rent.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(id);
                } else {
                    callback.onFailure("Error al rentar la propiedad");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure("Error al rentar la propiedad: " + t.getMessage());
            }
        });
    }

    // Método para obtener propiedades de un usuario
    public void myProperties(int id, PropertyListCallback callback) {
        Call<com.univerisity.Domain.models.Response> myProperties = service.MyProperties(id);
        myProperties.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Property> listProperties = (ArrayList<Property>) response.body().getValue();
                    if (listProperties.isEmpty()) {
                        callback.onFailure("No cuenta con propiedades registradas");
                    } else {
                        callback.onSuccess(listProperties);
                    }
                } else {
                    callback.onFailure("No se pudo obtener la lista de propiedades");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure("Error al obtener la lista de propiedades: " + t.getMessage());
            }
        });
    }

    //Metodo para guardar favoritos
    public void addFavorite(int idUser, int idProperty, PropertyCallback callback){
        Call<com.univerisity.Domain.models.Response> call = service.addFavorite(idUser, idProperty);
        call.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().isSucces()){
                        callback.onSuccess(idProperty);
                    }
                    else{
                        callback.onFailure("Fallo en el resultado");
                    }
                }
                else {
                    callback.onFailure("Error al guardar");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    //Metodo para listar favoritos
    public void getFavorities(int idUser, FavoriteCallback callback){
        Call<com.univerisity.Domain.models.Response> call = service.favoritiesList(idUser);
        call.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if (response.isSuccessful() && response.body() !=null){
                    callback.onSuccess(((ArrayList<Favorito>) response.body().getValueFavoritos()));
                }
                else {
                    callback.onFailure("Error al obetener la lista de favoritos");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    //Metodo para eliminar favoritos
    public void deleteFavorite(int idUser, int idProperty, PropertyCallback callback){
        Call<com.univerisity.Domain.models.Response> call = service.deleteFavorite(idUser, idProperty);
        call.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if(response.isSuccessful() && response.body() != null){
                    if (response.body().isSucces()){
                        callback.onSuccess(idProperty);
                    }
                    else{
                        callback.onFailure("Error al eliminar");
                    }
                }
                else{
                    callback.onFailure("Error al eliminar");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void propertiesSearch(String search, PropertyListCallback callback){
        Call<com.univerisity.Domain.models.Response> call = service.PropertiesSearch(search);
        call.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if(response.isSuccessful() && response.body() != null){
                    callback.onSuccess((ArrayList<Property>) response.body().getValueSearch());
                }
                else{
                    callback.onFailure("No se puedo listar las propiedades por filtro");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
