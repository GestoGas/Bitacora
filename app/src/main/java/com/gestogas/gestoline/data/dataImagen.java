package com.gestogas.gestoline.data;

public class dataImagen {
    int id;
    String ruta;
    String nombre;

    public dataImagen(int id, String url, String nombre) {
        this.id = id;
        this.ruta = url;
    }

    public int getId() {
        return id;
    }

    public String getRuta() {
        return ruta;
    }

    public String getNombre() {
        return nombre;
    }

}
