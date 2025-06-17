package com.gestogas.gestoline.data;

public class dataProfeco {

    public String id;
    public String nodispensario;
    public String folio;
    public String fecha;
    public String hora;
    public String productobitacora;
    public String lado;
    public String motivo;
    public String nombre;
    public String observaciones;
    public int estado;

    public dataProfeco(String id, String nodispensario, String folio, String fecha, String hora, String productobitacora, String lado, String motivo, String nombre, String observaciones, int estado) {

        this.id = id;
        this.nodispensario = nodispensario;
        this.folio = folio;
        this.fecha = fecha;
        this.hora = hora;
        this.productobitacora = productobitacora;
        this.lado = lado;
        this.motivo = motivo;
        this.nombre = nombre;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getNodispensario() {
        return nodispensario;
    }

    public String getFolio() {
        return folio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getProductobitacora() {
        return productobitacora;
    }

    public String getLado() {
        return lado;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public int getEstado() {
        return estado;
    }
}
