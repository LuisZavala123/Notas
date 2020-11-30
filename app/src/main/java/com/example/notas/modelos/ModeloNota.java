package com.example.notas.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class ModeloNota implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private String contenido;
    private String fecha;
    private ArrayList<ModeloMultimedia> notas_multimedia;

    public ModeloNota(int id, String titulo, String descripcion, String contenido, String fecha) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.fecha = fecha;
        this.notas_multimedia = new ArrayList<>();
    }

    public ModeloNota(String titulo, String descripcion, String contenido, String fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.fecha = fecha;
        this.notas_multimedia = new ArrayList<>();
    }

    public ModeloNota() {
        this.notas_multimedia = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ArrayList<ModeloMultimedia> getNotas_multimedia() {
        return notas_multimedia;
    }

    public void setNotas_multimedia(ArrayList<ModeloMultimedia> notas_multimedia) {
        this.notas_multimedia = notas_multimedia;
    }

    public void addNotas_multimedia(ModeloMultimedia multimedia) {
        this.notas_multimedia.add(multimedia);
    }

    public void deleteNotas_multimedia(ModeloMultimedia multimedia) {
        this.notas_multimedia.remove(multimedia);
    }
}
