package com.example.notas.modelos;

import java.io.Serializable;

public class ModeloMultimedia implements Serializable {
    public int id_multimedia;
    public int id;
    public String nombre;
    public String tipo;
    public String descripcion;
    public String ruta;

    public ModeloMultimedia(int id_multimedia, int id, String nombre, String tipo, String descripcion, String ruta) {
        this.id_multimedia = id_multimedia;
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ruta = ruta;
    }

    public ModeloMultimedia(int id, String nombre, String tipo, String descripcion, String ruta) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ruta = ruta;
    }

    public ModeloMultimedia(String nombre, String tipo, String descripcion, String ruta) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ruta = ruta;
    }

    public int getId_multimedia() {
        return id_multimedia;
    }

    public void setId_multimedia(int id_multimedia) {
        this.id_multimedia = id_multimedia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
