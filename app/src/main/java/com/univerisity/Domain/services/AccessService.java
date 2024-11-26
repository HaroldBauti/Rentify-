package com.univerisity.Domain.services;

import com.univerisity.DataAccess.DAOs.DAOAccess;
import com.univerisity.Common.utils.Message;

public class AccessService {
    private DAOAccess access;

    public AccessService(DAOAccess access){
        this.access = access;
    }

    public void login(String email, String password, DAOAccess.LoginCallback callback){
        if (email.isEmpty() || password.isEmpty()){
            Message.Error = "Correo o contraseña vacios";
            return;
        }

        access.Login(email, password, callback);
    }

    public void signin(String name, String lastname, String phone, String email, String password, DAOAccess.LoginCallback callback){
        access.signin(name, lastname, phone, email, password, callback);
    }
}
