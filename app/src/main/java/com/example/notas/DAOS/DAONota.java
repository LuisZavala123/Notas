package com.example.notas.DAOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.notas.modelos.ModeloMultimedia;
import com.example.notas.modelos.ModeloNota;

import java.util.ArrayList;

public class DAONota extends DAOdb {

    public DAONota(Context ctx) {
        super(ctx);
    }

    private ContentValues creaValores(ModeloNota nota) {
        ContentValues valores = new ContentValues();
        valores.put("titulo", nota.getTitulo());
        valores.put("descripcion", nota.getDescripcion());
        valores.put("contenido", nota.getContenido());
        valores.put("fecha", nota.getFecha());
        return valores;
    }

    public void insertaNota(ModeloNota nota){
        long idnota = _sql.insert("notas",
                null, creaValores(nota));
        DAOMultimedia daoMulti = new DAOMultimedia(super.context);
        for (int i = 0; i < nota.getNotas_multimedia().size(); i++) {
            daoMulti.insertMultiNota(nota.getNotas_multimedia().get(i), (int)idnota);
        }
    }

    public void eliminarNota(final int id) {
        ModeloNota nota = notabyId(id);
        DAOMultimedia daoMultimedia = new DAOMultimedia(super.context);
        for (int i = 0; i < nota.getNotas_multimedia().size(); i++) {
            daoMultimedia.deleteMultiNotas(id);
        }
        _sql.delete("notas",
                "id =" + id,
                null);
    }

    public void actualizarNota(ModeloNota nota) {
        _sql.update("notas",
                creaValores(nota),
                "id = " + nota.getId(),
                null);
    }

    public ModeloNota getNota(Cursor cursor) {
        ModeloNota nota = new ModeloNota(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        ArrayList<ModeloMultimedia> medios = new DAOMultimedia(super.context).getallMultiNotas(nota.getId());
        for (int i = 0; i < medios.size(); i++) {
            nota.addNotas_multimedia(medios.get(i));
        }
        return nota;
    }

    public ArrayList<ModeloNota> allNotas (){
        ArrayList<ModeloNota> notas = null;

        DAOMultimedia daoMulti = new DAOMultimedia(super.context);

        Cursor cursor = _sql.query("notas",
                database.COLUMNAS_NOTA,
                null,
                null,
                null,
                null,
                "fecha",
                null);

        if (cursor.moveToFirst() ){
            notas = new ArrayList<>();
            do {
                ModeloNota nota = getNota(cursor);
                notas.add(nota);
            }while(cursor.moveToNext());
        }
        cursor.close();
        if(notas == null){
            return new ArrayList<>();
        }
        for (int i = 0; i < notas.size(); i++) {
            notas.get(i).setNotas_multimedia(daoMulti.getallMultiNotas(notas.get(i).getId()));
        }
        return notas;
    }


    public ModeloNota notabyId(final int id) {
        Cursor cursor = _sql.query("notas",
                database.COLUMNAS_NOTA,
                "id = " + id,
                null,
                null,
                null,
                null,
                null);
        ModeloNota coso = null;
        if(cursor.moveToFirst()) {
            coso = getNota(cursor);
        }
        cursor.close();
        return coso;
    }

}
