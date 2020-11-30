package com.example.notas.DAOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.notas.modelos.ModeloMultimedia;

import java.util.ArrayList;

public class DAOMultimedia extends DAOdb{
    public DAOMultimedia(Context context){
        super(context);
    }


    private ContentValues creaValores(ModeloMultimedia multimedia, int id) {
        ContentValues valores = new ContentValues();
        valores.put("id", id);
        valores.put("nombre", multimedia.getNombre());
        valores.put("tipo", multimedia.getTipo());
        valores.put("descripcion", multimedia.getDescripcion());
        valores.put("ruta", multimedia.getRuta());
        return valores;
    }

    public void insertMultiNota(ModeloMultimedia multimedia, int id){
        _sql.insert("multinotas",
                null, creaValores(multimedia, id));
    }

    public void insertMultiTareas(ModeloMultimedia multimedia, int id){
        _sql.insert("multitareas",
                null, creaValores(multimedia, id));
    }

    public void deleteMultiNotas(final int id) {
        _sql.delete("multinotas",
                "id =" + id,
                null);
    }

    public void deleteMultiTareas(final int id) {
        _sql.delete("multitareas",
                "id =" + id,
                null);
    }

    public void updateMultiNotas(ModeloMultimedia multimedia, int id) {
        _sql.update("multinotas",
                creaValores(multimedia, id),
                "id_multimedia =" + multimedia.getId(),
                null);
    }

    public void updateMultiTareas(ModeloMultimedia multimedia, int id) {
        _sql.update("multitareas",
                creaValores(multimedia, id),
                "id_multimedia =" + multimedia.getId(),
                null);
    }

    public ModeloMultimedia getMultimedia(Cursor cursor) {
        ModeloMultimedia multimedia = new ModeloMultimedia(cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        return multimedia;
    }

    public ArrayList<ModeloMultimedia> getMultimedias(Cursor cursor){
        ArrayList<ModeloMultimedia> Lista = null;
        if(cursor.moveToFirst()){
            Lista = new ArrayList<>();
            do {
                ModeloMultimedia multimedia = getMultimedia(cursor);
                Lista.add(multimedia);
            } while (cursor.moveToNext());
        }
        if(Lista == null) {
            return new ArrayList<>();
        }
        cursor.close();
        return Lista;
    }

    public ArrayList<ModeloMultimedia> getallMultiNotas(){
        Cursor cursor = _sql.query("multinotas", database.COLUMNAS_MULTINOTA, null, null, null, null, null, null);
        return getMultimedias(cursor);
    }

    public ArrayList<ModeloMultimedia> getallMultiTareas(){
        Cursor cursor = _sql.query("multitaeras", database.COLUMNAS_MULTITAREA, null, null, null, null, null, null);
        return getMultimedias(cursor);
    }

    public Cursor getCursorMultiNotas(int id){
        Cursor cursor = _sql.query("multinotas", database.COLUMNAS_MULTINOTA, "id = " + id, null, null, null, null);
        return cursor;
    }

    public ArrayList<ModeloMultimedia> getallMultiNotas(int id){
        Cursor cursor = getCursorMultiNotas(id);
        return getMultimedias(cursor);
    }

    public Cursor getCursorMultiTareas(int id){
        Cursor cursor = _sql.query("multitareas", database.COLUMNAS_MULTITAREA, "id = " + id, null, null, null, null);
        return cursor;
    }

    public ArrayList<ModeloMultimedia> getallMultiTareas(int id){
        Cursor cursor = getCursorMultiTareas(id);
        return getMultimedias(cursor);
    }
}
