package com.example.notas.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class ModeloTarea implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private String contenido;
    private String fecha;
    private String fecha_cumplir;
    private ArrayList<ModeloRecordatorio> recordatorios;
    private ArrayList<ModeloMultimedia> multimedias;

    public ModeloTarea(int id, String titulo, String descripcion, String contenido, String fecha, String fecha_cumplir) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.fecha = fecha;
        this.fecha_cumplir = fecha_cumplir;
        this.recordatorios = new ArrayList<>();
        this.multimedias = new ArrayList<>();
    }

    public ModeloTarea(String titulo, String descripcion, String contenido, String fecha, String fecha_cumplir) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.fecha = fecha;
        this.fecha_cumplir = fecha_cumplir;
        this.recordatorios = new ArrayList<>();
        this.multimedias = new ArrayList<>();
    }

    public ModeloTarea() {
        this.recordatorios = new ArrayList<>();
        this.multimedias = new ArrayList<>();
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

    public String getFecha_cumplir() {
        return fecha_cumplir;
    }

    public void setFecha_cumplir(String fecha_cumplir) {
        this.fecha_cumplir = fecha_cumplir;
    }

    public ArrayList<ModeloRecordatorio> getRecordatorios() {
        return recordatorios;
    }

    public void setRecordatorios(ArrayList<ModeloRecordatorio> recordatorios) {
        this.recordatorios = recordatorios;
    }

    public void addRecordatorio(ModeloRecordatorio recordatorio) {
        this.recordatorios.add(recordatorio);
    }

    public void deleteRecordatorio(ModeloRecordatorio recordatorios) {
        this.recordatorios.remove(recordatorios);
    }

    public ArrayList<ModeloMultimedia> getMultimedias() {
        return multimedias;
    }

    public void setMultimedias(ArrayList<ModeloMultimedia> multimedias) {
        this.multimedias = multimedias;
    }

    public void addMultimedia(ModeloMultimedia multimedia) {
        this.multimedias.add(multimedia);
    }

    public void deleteMultimedia(ModeloMultimedia multimedia) {
        this.multimedias.remove(multimedia);
    }
}
