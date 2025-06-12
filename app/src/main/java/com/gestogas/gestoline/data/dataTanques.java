package com.gestogas.gestoline.data;

public class dataTanques {

    public int id;
    private String descripcion;
    public String notanque;
    public String capacidad;
    public String producto;

    public dataTanques(int id, String notanque, String capacidad, String producto){
        this.id = id;
        this.notanque = notanque;
        this.capacidad = capacidad;
        this.producto = producto;

    }

    public void TanqueItem(int idTanque, String descripcion){
        this.id = idTanque;
        this.descripcion = descripcion;
    }

    public int getId() {return id;}
    public String getNotanque() {return notanque;}
    public String getCapacidad() {return capacidad;}

    public String getProducto() {return producto;}

    @Override
    public String toString() {
        return (this.id != 0) ? "Tanque: " + notanque + ", " + producto : "Seleccione";
    }
}
