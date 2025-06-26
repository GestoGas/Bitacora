package com.gestogas.gestoline.data;

import java.util.Objects;

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

    @Override
    public String toString() {
        return (this.id != 0) ?  nombreusuario : "Seleccione";
    }
}
