package com.example.notas.DAOS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DAOdb {
    final SQLiteDatabase _sql;
    final Context context;

    public DAOdb(Context context){
        this.context = context;
        this._sql = new database(context).getWritableDatabase();
    }
}
