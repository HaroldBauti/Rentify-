package com.univerisity.DataAccess.DAOs;

import com.univerisity.Common.profile.Profile;
import com.univerisity.DataAccess.api.ApiService;
import com.univerisity.DataAccess.api.ApiClient;
import com.univerisity.Common.utils.Message;
import com.univerisity.Domain.models.Login;
import com.univerisity.Domain.models.User;
import com.univerisity.Domain.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DAOAccess {

    private ApiService service;

    public DAOAccess(){
        service = ApiClient.getApiService();
    }

    // Callback para manejar el resultado
    public interface LoginCallback {
        void onLoginSuccess();
        void onLoginFailure(String errorMessage);
    }

    public void Login(String email, String password, LoginCallback callback){
        Call<Login> log = service.login(email, password);
        log.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().isSucces() > 0){
                        setUser(response.body().isSucces(), password);
                        callback.onLoginSuccess();
                    }
                    else {
                        Message.Error = "Correo o contraseña incorrectas";
                        callback.onLoginFailure("Correo o contraseña incorrectas");
                    }
                }
                else{
                    Message.Error = "Error al iniciar sesión, intentelo nuevamente";
                    callback.onLoginFailure("Error al iniciar sesión, intentelo nuevamente");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Message.Error = t.getMessage();
                callback.onLoginFailure(t.getMessage());
            }
        });
    }

    public void signin(String name, String lastname, String phone, String email, String password, LoginCallback callback){
        Call<com.univerisity.Domain.models.Response> signin = service.register(name, lastname, phone, email, password);
        signin.enqueue(new Callback<com.univerisity.Domain.models.Response>() {
            @Override
            public void onResponse(Call<com.univerisity.Domain.models.Response> call, Response<com.univerisity.Domain.models.Response> response) {
                if(response.isSuccessful() && response.body() != null){
                    if (response.body().isSucces()){
                        callback.onLoginSuccess();
                    }
                    else{
                        callback.onLoginFailure("No se pudo registrar, intentelo nuevamente");
                    }
                }
                else{
                    callback.onLoginFailure("Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<com.univerisity.Domain.models.Response> call, Throwable t) {
                Message.Error = t.getMessage();
                callback.onLoginFailure(t.getMessage());
            }
        });
    }

    private void setUser(int id, String password){
        UserService uService = new UserService();
        uService.getUser(id, new DAOUser.UserCallback() {
            @Override
            public void onLoginSuccess(User user) {
                Profile.setIdRauser(user.getIdRauser());
                Profile.setNombre(user.getNombre());
                Profile.setApellidos(user.getApellidos());
                Profile.setCelular(user.getCelular());
                Profile.setEmail(user.getEmail());
                Profile.setClave(password);
                Profile.setTipo(user.isTipo());
            }

            @Override
            public void onLoginFailure(String errorMessage) {

            }
        });
    }
}
