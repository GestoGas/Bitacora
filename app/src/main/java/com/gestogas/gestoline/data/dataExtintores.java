package com.gestogas.gestoline.data;

public class dataExtintores {

    public String id;
    public String noextintor;
    public String ubicacion;
    public String fecha;
    public String fecharecarga;
    public String tipoextintor;
    public String pesokg;

    public dataExtintores(String id, String noextintor, String ubicacion, String fecha, String fecharecarga, String tipoextintor, String pesokg){

        this.id = id;
        this.noextintor = noextintor;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.fecharecarga = fecharecarga;
        this.tipoextintor = tipoextintor;
        this.pesokg = pesokg;

    }


    public String getId() {
        return id;
    }

    public String getNoextintor() {
        return noextintor;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFecharecarga() {
        return fecharecarga;
    }

    public String getTipoextintor() {
        return tipoextintor;
    }

    public String getPesokg() {
        return pesokg;
    }
}
