package com.univerisity.Domain.services;

import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOProperty;
import com.univerisity.Domain.models.Property;

import java.util.ArrayList;

public class PropertyService {
    private DAOProperty property;

    public PropertyService(){
        property = new DAOProperty();
    }

    public void getAllProperties(DAOProperty.PropertyListCallback callback){
        property.getAllProperties(callback);
    }

    //Metodo para obtener una propiedad
    public void getProperty(int id, DAOProperty.GetProptertyCallback callback){
        property.getProperty(id, callback);
    }

    public void saveProperty(int idUser, Property prop, DAOProperty.PropertyCallback callback){
       property.saveProperty(idUser, prop, callback);
    }

    public void editProperty(int id, Property prop, DAOProperty.PropertyCallback callback){
        property.editProperty(id, prop, callback);
    }

    public void rentProperty(int id, DAOProperty.PropertyCallback callback){
        property.rentProperty(id, callback);
    }

    public void myProperties(int id, DAOProperty.PropertyListCallback callback){
        property.myProperties(id, callback);
    }

    public void getFavorities(int idUser, DAOProperty.FavoriteCallback callback){
        property.getFavorities(idUser, callback);
    }

    public void addFavority(int idUser, int idProperty, DAOProperty.PropertyCallback callback){
        property.addFavorite(idUser, idProperty, callback);
    }

    public void deleteFavorite(int idUser, int idProperty, DAOProperty.PropertyCallback callback){
        property.deleteFavorite(idUser, idProperty, callback);
    }

    public void propertiesSearch(String search, DAOProperty.PropertyListCallback callback){
        property.propertiesSearch(search, callback);
    }
}
