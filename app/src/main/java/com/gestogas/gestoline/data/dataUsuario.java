package com.gestogas.gestoline.data;

public class dataUsuario {

    public int id;
    public String nombreusuario;

    public dataUsuario(int idusuario, String nombre){
        this.id = idusuario;
        this.nombreusuario = nombre;

    }

    public int getId() {
        return id;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }
}
