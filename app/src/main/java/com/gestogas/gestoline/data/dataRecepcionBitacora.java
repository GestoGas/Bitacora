package com.gestogas.gestoline.data;

public class dataRecepcionBitacora {


    public int id;
    public String folio;
    public String fecha;
    public String horallegada;
    public String horasalidad;
    public String nofactura;
    public String litroscompra;
    public String producto;
    public String inventarioinicial;
    public String inventariofinal;
    public String merma;
    public String observaciones;

    public int estado;

    public dataRecepcionBitacora(int id, String folio, String fecha, String horallegada, String horasalidad, String nofactura,
                                 String litroscompra, String producto, int estado){
        this.id = id;
        this.folio = folio;
        this.fecha = fecha;
        this.horallegada = horallegada;
        this.horasalidad = horasalidad;
        this.nofactura = nofactura;
        this.litroscompra = litroscompra;
        this.producto = producto;
        this.estado = estado;

    }


    public int getId() {
        return id;
    }

    public String getFolio() {
        return folio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHorallegada() {
        return horallegada;
    }

    public String getHorasalidad() {
        return horasalidad;
    }

    public String getNofactura() {
        return nofactura;
    }

    public String getLitroscompra() {
        return litroscompra;
    }

    public String getProducto() {
        return producto;
    }

    public String getInventarioinicial() {
        return inventarioinicial;
    }

    public String getInventariofinal() {
        return inventariofinal;
    }

    public String getMerma() {
        return merma;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public int getEstado() {
        return estado;
    }


}
