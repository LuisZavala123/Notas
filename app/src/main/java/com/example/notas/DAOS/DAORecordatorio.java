package com.example.notas.DAOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.notas.modelos.ModeloRecordatorio;

import java.util.ArrayList;

public class DAORecordatorio extends DAOdb {

    public DAORecordatorio(Context context){
        super(context);
    }

    private ContentValues crearValores(ModeloRecordatorio recordatorio, int id){
        ContentValues valores = new ContentValues();
        valores.put("id_tarea", id);
        valores.put("fecha", recordatorio.getFecha());
        return valores;
    }

    public void insert(ModeloRecordatorio recordatorio, int id){
        _sql.insert("recordatorios", null, crearValores(recordatorio, id));
    }

    public void delete(final int id_rec){
        _sql.delete("id_tarea", "id_tarea = " + id_rec, null);
    }

    public void update(ModeloRecordatorio recordatorio, int id_tarea){
        _sql.update("recordatorios", crearValores(recordatorio, id_tarea), "id = " + recordatorio.getId(), null);
    }

    public ArrayList<ModeloRecordatorio> getRecordatorios(){
        Cursor cursor = _sql.query("recordatorios", database.COLUMNAS_RECORDATORIOS, null, null, null, null, null, null);
        return getRecordatorios(cursor);
    }

    public ArrayList<ModeloRecordatorio> getRecordatoriosByTarea(int id){
        Cursor cursor = CursorRecordatoriosByTarea(id);
        return getRecordatorios(cursor);
    }

    public Cursor CursorRecordatoriosByTarea(int id){
        Cursor cursor = _sql.query("recordatorios",
                database.COLUMNAS_RECORDATORIOS,
                "id_tarea = " + id,
                null, null, null, null);
        return cursor;
    }

    public ArrayList<ModeloRecordatorio> getRecordatorios(Cursor cursor){
        ArrayList<ModeloRecordatorio> Lista = null;
        if(cursor.moveToFirst()){
            Lista = new ArrayList<>();
            do {
                ModeloRecordatorio recordatorio = getRecordatorio(cursor);
                Lista.add(recordatorio);
            } while (cursor.moveToNext());
        }
        if(Lista == null) {
            return new ArrayList<>();
        }
        cursor.close();
        return Lista;
    }

    public ModeloRecordatorio getRecordatorio(Cursor cursor){
        ModeloRecordatorio rec = new ModeloRecordatorio(cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2));
        return rec;
    }
}
