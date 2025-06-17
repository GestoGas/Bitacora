package com.gestogas.gestoline.data;

import java.util.Objects;

public class dataDispensarios {

    public String id;
    public String nodispensario;
    public String marca;
    public String modelo;
    public String serie;
    public String producto1;
    public String producto2;
    public String producto3;
    public String nomproducto1;
    public String nomproducto2;
    public String nomproducto3;

    public dataDispensarios(String id, String nodispensario, String marca, String modelo, String serie,
                            String nomproducto1, String producto1,
                            String nomproducto2, String producto2,
                            String nomproducto3, String producto3){
        this.id = id;
        this.nodispensario = nodispensario;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.nomproducto1 = nomproducto1;
        this.producto1 = producto1;
        this.nomproducto2 = nomproducto2;
        this.producto2 = producto2;
        this.nomproducto3 = nomproducto3;
        this.producto3 = producto3;

    }

    public String getId() {
        return id;
    }

    public String getNodispensario() {
        return nodispensario;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSerie() {
        return serie;
    }

    public String getProducto1() {
        return producto1;
    }

    public String getProducto2() {
        return producto2;
    }

    public String getProducto3() {
        return producto3;
    }

    public String getNomproducto1() {
        return nomproducto1;
    }

    public String getNomproducto2() {
        return nomproducto2;
    }

    public String getNomproducto3() {
        return nomproducto3;
    }

    @Override
    public String toString() {
        return (!Objects.equals(this.id, "0")) ? "Dispensario: " + nodispensario + ", " + marca : "Seleccione";
    }

}
