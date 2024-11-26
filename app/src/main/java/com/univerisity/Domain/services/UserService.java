package com.univerisity.Domain.services;

import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOUser;
import com.univerisity.Domain.models.User;

public class UserService {
    private DAOUser user;

    public UserService(){
        this.user = new DAOUser();
    }

    public void changeMode(int id, DAOUser.UserChangeCallback callback){
        this.user.changeMode(id, callback);
    }

    public void getUser(int id, DAOUser.UserCallback callback){
       this.user.getUser(id, callback);
    }

    public void updateUser(int id, User user, DAOUser.UserChangeCallback callback){
        this.user.updateUser(id, user, callback);
    }

    public void getEmail(String email, DAOUser.EmailCallback callback){
        this.user.getEmail(email, callback);
    }

    public void changePassword(String password, int idUser, DAOUser.UserChangeCallback callback){
        this.user.changePassword(password, idUser, callback);
    }
}
