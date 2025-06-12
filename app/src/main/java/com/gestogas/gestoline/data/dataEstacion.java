package com.gestogas.gestoline.data;

public class dataEstacion {

    public int id;
    public String permisocre;
    public String razonsocial;
    public String direccioncompleta;
    public String productouno;
    public String productodos;
    public String productotres;
    public String logo;
    public String latitud;
    public String longitud;
    public String distmax;

    public dataEstacion(int idestacion, String permisocre, String razonsocial, String direccioncompleta, String productouno, String productodos, String productotres,
                        String logo, String latitud, String longitud, String distmax){
        this.id = idestacion;
        this.permisocre = permisocre;
        this.razonsocial = razonsocial;
        this.direccioncompleta = direccioncompleta;
        this.productouno = productouno;
        this.productodos = productodos;
        this.productotres = productotres;
        this.logo = logo;
        this.longitud = longitud;
        this.latitud = latitud;
        this.distmax = distmax;
    }

    public int getId() {
        return id;
    }
    public String getPermisocre() {
        return permisocre;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public String getDireccioncompleta() {
        return direccioncompleta;
    }

    public String getProductouno() {
        return productouno;
    }

    public String getProductodos() {
        return productodos;
    }

    public String getProductotres() {
        return productotres;
    }

    public String getLogo() {
        return logo;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {return longitud;}

    public String getDistmax() {return distmax;}

}
