package com.univerisity.DataAccess.DAOs;

import com.univerisity.DataAccess.api.ApiClient;
import com.univerisity.DataAccess.api.ApiService;
import com.univerisity.Domain.models.Image;
import com.univerisity.Domain.models.Property;
import com.univerisity.Domain.models.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class DAOImages {
    private ApiService service;

    public DAOImages(){
        service = ApiClient.getApiService();
    }

    public interface ImageCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface ImageListCallback {
        void onSuccess(ArrayList<Image> images);
        void onFailure(String errorMessage);
    }

    public void saveImages(Image image, ImageCallback callback){
        Call<Response> call = service.saveImages(image.getIdPropiedad(), image.getImagenUrl(), image.getTitulo());
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null){
                    if(response.body().isSucces()){
                        callback.onSuccess();
                    }
                    else{
                        callback.onFailure("No se guardo la imagen");
                    }
                }
                else{
                    callback.onFailure("Error al guardar imagenes");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getImages(int idProperty, ImageListCallback callback){
        Call<Response> call = service.getImages(idProperty);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful() && response.body() != null){
                    callback.onSuccess((ArrayList<Image>) response.body().getValueImagenes());
                }
                else{
                    callback.onFailure("Error al obtener las imagenes");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
