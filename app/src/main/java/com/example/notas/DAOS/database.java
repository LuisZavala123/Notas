package com.example.notas.DAOS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    public database(@Nullable Context context) {
        super(context,
                "database",
                null,
                1);
    }

    private final String NOTAS = "CREATE TABLE " + "notas" + "(" +
            "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "titulo" + " TEXT NOT NULL," +
            "descripcion" + " TEXT NOT NULL," +
            "contenido" + " TEXT NOT NULL," +
            "fecha" + " DATE NOT NULL" +
            ");";

    public static final String[] COLUMNAS_NOTA = {
            "id",
            "titulo",
            "descripcion",
            "contenido",
            "fecha"
    };

    private final String TAREAS = "CREATE TABLE " + "tareas" + "(" +
            "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "titulo" + " TEXT NOT NULL," +
            "descripcion" + " TEXT NOT NULL," +
            "contenido" + " TEXT NOT NULL," +
            "fecha" + " DATE NOT NULL," +
            "fecha_cumplir" + " DATE NOT NULL" +
            ");";

    public static final String[] COLUMNAS_TAREA = {
            "id",
            "titulo",
            "descripcion",
            "contenido",
            "fecha",
            "fecha_cumplir",
    };

    private final String MULTIMEDIA_NOTA = "CREATE TABLE " + "multinotas" + "(" +
            "id_multimedia" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id" + " INTEGER NOT NULL," +
            "nombre" + " TEXT NOT NULL," +
            "tipo" + " TEXT NOT NULL," +
            "descripcion" + " TEXT NOT NULL," +
            "ruta" + " TEXT NOT NULL," +
            "FOREIGN KEY(" + "id" + ") REFERENCES " + "notas" + "(" + "id" + ")" +
            ");";

    public static final String[] COLUMNAS_MULTINOTA = {
            "id_multimedia",
            "id",
            "nombre",
            "tipo",
            "descripcion",
            "ruta"
    };

    private final String MULTIMEDA_TAREA = "CREATE TABLE " + "multitareas" + "(" +
            "id_multimedia" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id" + " INTEGER NOT NULL," +
            "nombre" + " TEXT NOT NULL," +
            "tipo" + " TEXT NOT NULL," +
            "descripcion" + " TEXT NOT NULL," +
            "ruta" + " TEXT NOT NULL," +
            "FOREIGN KEY(" + "id" + ") REFERENCES " + "tareas" + "(" + "id" + ")" +
            ");";

    public static final String[] COLUMNAS_MULTITAREA = {
            "id_multimedia",
            "id",
            "nombre",
            "tipo",
            "descripcion",
            "ruta"
    };

    private final String RECORDATORIOS_TAREA = "CREATE TABLE " + "recordatorios" + "(" +
            "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_tarea" + " INTEGER NOT NULL," +
            "fecha" + " DATE NOT NULL," +
            "FOREIGN KEY(" + "id_tarea" + ") REFERENCES " + "tareas" + "(" + "id" + ")" +
            ");";

    public static final String[] COLUMNAS_RECORDATORIOS = {
            "id",
            "id",
            "fecha"
    };

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NOTAS);
        sqLiteDatabase.execSQL(TAREAS);
        sqLiteDatabase.execSQL(MULTIMEDIA_NOTA);
        sqLiteDatabase.execSQL(MULTIMEDA_TAREA);
        sqLiteDatabase.execSQL(RECORDATORIOS_TAREA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
