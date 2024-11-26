package com.univerisity.Domain.services;

import com.univerisity.DataAccess.DAOs.DAOImages;
import com.univerisity.Domain.models.Image;

import java.util.ArrayList;

public class ImageService {
    private DAOImages images;

    public ImageService(){
        images = new DAOImages();
    }

    public void saveImage(Image image, DAOImages.ImageCallback callback){
        this.images.saveImages(image, callback);
    }

    public void getImages(int idProperty, DAOImages.ImageListCallback callback){
        this.images.getImages(idProperty, callback);
    }

}
