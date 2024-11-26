package com.univerisity.Domain.models;

public class Image {
    private int idPropiedad;
    private String titulo;
    private String imagenUrl;

    public Image() {
    }

    public Image(String titulo, String imagenUrl, int idPropiedad) {
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.idPropiedad = idPropiedad;
    }

    public int getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
