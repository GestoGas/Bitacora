package com.gestogas.gestoline.data;

public class dataPersonal {

    public String nombreusuario;
    public int idfirma;
    public String categoria;

    public dataPersonal(int idfirma, String nombreusuario, String categoria){
        this.nombreusuario = nombreusuario;
        this.idfirma = idfirma;
        this.categoria = categoria;
    }



    public String getNombreusuario() {
        return nombreusuario;
    }

    public int getIdfirma() {
        return idfirma;
    }

    public String getCategoria() {
        return categoria;
    }




}
