package com.example.notas.modelos;

import java.io.Serializable;

public class ModeloRecordatorio implements Serializable {

    private int id;
    private int id_tarea;
    private String fecha;

    public ModeloRecordatorio(int id, int id_tarea, String fecha) {
        this.id = id;
        this.id_tarea = id_tarea;
        this.fecha = fecha;
    }

    public ModeloRecordatorio( int id_tarea, String fecha) {
        this.id_tarea = id_tarea;
        this.fecha = fecha;
    }

    public ModeloRecordatorio(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
