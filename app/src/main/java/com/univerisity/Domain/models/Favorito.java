package com.univerisity.Domain.models;

import java.util.Date;

public class Favorito {
    private Property oProperty;
    private Date fechaAdd;

    public Favorito() {
    }

    public Favorito(Property oProperty, Date fechaAdd) {
        this.oProperty = oProperty;
        this.fechaAdd = fechaAdd;
    }

    public Property getoProperty() {
        return oProperty;
    }

    public void setoProperty(Property oProperty) {
        this.oProperty = oProperty;
    }

    public Date getFechaAdd() {
        return fechaAdd;
    }

    public void setFechaAdd(Date fechaAdd) {
        this.fechaAdd = fechaAdd;
    }

    @Override
    public String toString() {
        return "Favorito{" +
                "oProperty=" + oProperty +
                ", fechaAdd=" + fechaAdd +
                '}';
    }
}
