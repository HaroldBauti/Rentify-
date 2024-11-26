package com.univerisity.DataAccess.DAOs;

import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.api.ApiClient;
import com.univerisity.DataAccess.api.ApiService;
import com.univerisity.Domain.models.Login;
import com.univerisity.Domain.models.Response;
import com.univerisity.Domain.models.User;

import retrofit2.Call;
import retrofit2.Callback;

public class DAOUser {
    private ApiService service;

    public DAOUser(){
        service = ApiClient.getApiService();
    }

    // Callback para manejar el resultado de cambios
    public interface UserChangeCallback {
        void onLoginSuccess();
        void onLoginFailure(String errorMessage);
    }

    // Callback para manejar el resultado de cambios
    public interface UserCallback {
        void onLoginSuccess(User user);
        void onLoginFailure(String errorMessage);
    }

    public  interface EmailCallback{
        void onLoginSuccess(int idUser);
        void onLoginFailure(String errorMessage);
    }

    public void changeMode(int id, UserChangeCallback callback){
        Call<Response> change = service.cambiarModo(id);
        change.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful() && response.body() != null){
                    Message.Success = "Cambio de modo de usuario exitoso";
                    callback.onLoginSuccess();
                }
                else{
                    callback.onLoginFailure("No se pudo cambiar de modo, intentelo nuevamente");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callback.onLoginFailure(t.getMessage());
            }
        });
    }

    public void getUser(int id, UserCallback callback){
        Call<Response> call = service.infoUsuario(id);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onLoginSuccess(response.body().getUsuario());
                }
                else{
                    callback.onLoginFailure("Error al obtener informacion del usuario");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callback.onLoginFailure(t.getMessage());
            }
        });
    }

    public void updateUser(int id, User user, UserChangeCallback callback){
        Call<Response> update = service.updateUser(id, user.getNombre(), user.getApellidos(), user.getCelular(), user.getEmail(), user.getClave());
        update.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().isSuccess()){
                        Message.Success = "Usuario actualizado correctamente";
                        callback.onLoginSuccess();
                    }
                    else{
                        callback.onLoginFailure("No se pudo actualizar al usuario");
                    }
                }
                else{
                    callback.onLoginFailure("No se pudo actualizar al usuario");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callback.onLoginFailure(t.getMessage());
            }
        });
    }

    public void getEmail(String email, EmailCallback callback){
        Call<Login> call = service.getEmail(email);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, retrofit2.Response<Login> response) {
                if(response.isSuccessful() && response.body() != null){
                    if (response.body().isSucces() > 0){
                        callback.onLoginSuccess(response.body().isSucces());
                    }
                    else{
                        callback.onLoginFailure("Error de id");
                    }
                }
                else {
                    callback.onLoginFailure("Error al buscar email");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                callback.onLoginFailure(t.getMessage());
            }
        });
    }

    public void changePassword(String password, int idUser, UserChangeCallback callback){
        Call<Response> call = service.changePassword(password, idUser);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().isSuccess()){
                        callback.onLoginSuccess();
                    }
                    else{
                        callback.onLoginFailure("Erro al actualizar la contraseña");
                    }
                }
                else{
                    callback.onLoginFailure("Erro al actualizar la contraseña");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callback.onLoginFailure(t.getMessage());
            }
        });
    }
}
