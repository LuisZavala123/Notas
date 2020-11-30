package com.example.notas.DAOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.notas.modelos.ModeloMultimedia;
import com.example.notas.modelos.ModeloTarea;

import java.util.ArrayList;

public class DAOTarea extends DAOdb {

    public DAOTarea(Context context) {
        super(context);
    }

    private ContentValues creaValores(ModeloTarea tarea) {
        ContentValues valores = new ContentValues();
        valores.put("titulo", tarea.getTitulo());
        valores.put("descripcion", tarea.getDescripcion());
        valores.put("contenido", tarea.getContenido());
        valores.put("fecha", tarea.getFecha());
        valores.put("fecha_cumplir", tarea.getFecha_cumplir());
        return valores;
    }

    public void insertarTarea(ModeloTarea tarea){
        long id = _sql.insert("tareas",
                null, creaValores(tarea));
        DAOMultimedia daoMultimedia = new DAOMultimedia(super.context);
        for (int i = 0; i < tarea.getMultimedias().size(); i++) {
            daoMultimedia.insertMultiNota(tarea.getMultimedias().get(i), (int) id);
        }
    }

    public void eliminarTarea(final int id) {
        ModeloTarea tarea = tareaById(id);
        DAOMultimedia daoMulti = new DAOMultimedia(super.context);
        DAORecordatorio daoRec = new DAORecordatorio(super.context);
        for (int i = 0; i < tarea.getMultimedias().size(); i++) {
            daoMulti.deleteMultiTareas(id);
        }
        for (int i = 0; i < tarea.getRecordatorios().size(); i++) {
            daoRec.delete(id);
        }
        _sql.delete("tareas",
                "id =" + id,
                null);
    }

    public void actualizarTarea(ModeloTarea tarea) {
        _sql.update("tareas",
                creaValores(tarea),
                "id = " + tarea.getId(),
                null);
    }

    public ModeloTarea getTarea(Cursor cursor) {
        ModeloTarea tarea = new ModeloTarea(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        ArrayList<ModeloMultimedia> multimedias = new DAOMultimedia(super.context).getallMultiTareas(tarea.getId());
        for (int i = 0; i < multimedias.size(); i++) {
            tarea.addMultimedia(multimedias.get(i));
        }
        return tarea;
    }

    public ArrayList<ModeloTarea> allTareas (){
        ArrayList<ModeloTarea> Lista = null;
        DAOMultimedia daoMultimedia = new DAOMultimedia(super.context);
        DAORecordatorio daoRecordatorio = new DAORecordatorio(super.context);
        Cursor cursor = _sql.query("tareas",
                database.COLUMNAS_TAREA,
                null,
                null,
                null,
                null,
                "fecha",
                null);

        if (cursor.moveToFirst() ){
            Lista = new ArrayList<>();
            do {
                ModeloTarea tarea = getTarea(cursor);
                Lista.add(tarea);
            }while(cursor.moveToNext());
        }
        cursor.close();
        if(Lista == null){
            return new ArrayList<>();
        }
        for (int i = 0; i < Lista.size(); i++) {
            Lista.get(i).setMultimedias(daoMultimedia.getallMultiNotas(Lista.get(i).getId()));
            Lista.get(i).setRecordatorios(daoRecordatorio.getRecordatoriosByTarea(Lista.get(i).getId()));
        }
        return Lista;
    }


    public ModeloTarea tareaById(final int id) {
        Cursor cursor = _sql.query("tareas",
                database.COLUMNAS_TAREA,
                "id = " + id,
                null,
                null,
                null,
                null,
                null);
        ModeloTarea tarea = null;
        if(cursor.moveToFirst()) {
            tarea = getTarea(cursor);
        }
        cursor.close();
        return tarea;
    }
}
