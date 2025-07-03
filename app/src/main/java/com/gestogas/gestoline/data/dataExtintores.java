package com.gestogas.gestoline.data;

public class dataExtintores {

    public String id;
    public String noextintor;
    public String ubicacion;
    public String fecha;
    public String fecharecarga;
    public String tipoextintor;
    public String pesokg;
    public String idmantenimiento;
    public String ultimarecarga;
    public String manometro;
    public String boquilladescarga;
    public String manguera;
    public String funcionalidad;
    public String observaciones;
    public String FPR;
    public String FPS;
    public String PFPR;
    public String PFPS;

    public dataExtintores(String id, String noextintor, String ubicacion, String fecha, String fecharecarga, String tipoextintor, String pesokg,
                          String idmantenimiento, String ultimarecarga, String manometro,
                          String boquilladescarga, String manguera, String funcionalidad, String observaciones,
                          String FPR, String FPS, String PFPR, String PFPS){

        this.id = id;
        this.noextintor = noextintor;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.fecharecarga = fecharecarga;
        this.tipoextintor = tipoextintor;
        this.pesokg = pesokg;
        this.idmantenimiento = idmantenimiento;
        this.ultimarecarga = ultimarecarga;
        this.manometro = manometro;
        this.boquilladescarga = boquilladescarga;
        this.manguera = manguera;
        this.funcionalidad = funcionalidad;
        this.observaciones = observaciones;
        this.FPR = FPR;
        this.FPS = FPS;
        this.PFPR = PFPR;
        this.PFPS = PFPS;

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
    public String getIdmantenimiento() {return idmantenimiento;}
    public String getUltimarecarga() {return ultimarecarga;}
    public String getManometro() {return manometro;}
    public String getBoquilladescarga() {return boquilladescarga;}
    public String getManguera() {return manguera;}
    public String getFuncionalidad() {return funcionalidad;}
    public String getObservaciones() {return observaciones;}
    public String getFPR() {return FPR;}
    public String getFPS() {return FPS;}
    public String getPFPR() {return PFPR;}
    public String getPFPS() {return PFPS;}


}
