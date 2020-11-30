package com.example.notas.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.Activity_agregar;
import com.example.notas.DAOS.DAOTarea;
import com.example.notas.R;
import com.example.notas.adapters.AdapterTarea;
import com.example.notas.modelos.ModeloTarea;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentoTareas extends Fragment implements AdapterTarea.longclick {

    RecyclerView recycler;
    FloatingActionButton refrescar;
    DAOTarea daoTarea;

    public FragmentoTareas() {

    }

    List<ModeloTarea> lista_tareas;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.listatareas, container, false);
        recycler = root.findViewById(R.id.listatareasrecycler);
        refrescar =  root.findViewById(R.id.refreshTareas);
        //refrescarTareas
        recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        lista_tareas = new DAOTarea(getContext()).allTareas();
        adapter = new AdapterTarea(getContext(), lista_tareas, this);
        recycler.setAdapter(adapter);
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescartareas();
            }
        });
        return root;
    }

    public void refrescartareas(){
        recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        lista_tareas = new DAOTarea(getContext()).allTareas();
        adapter = new AdapterTarea(getContext(), lista_tareas, this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void longclickTarea(final int position) {
        final CharSequence[] items = {"Editar", "Eliminar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: //Editar
                        Intent intent = new Intent(getContext(), Activity_agregar.class);
                        intent.putExtra("tarea", lista_tareas.get(position));
                        startActivity(intent);
                        break;
                    case 1: //Eliminar
                        daoTarea = new DAOTarea(getContext());
                        daoTarea.eliminarTarea(lista_tareas.get(position).getId());
                        Toast.makeText(getContext(), "Se eliminó el registro.", Toast.LENGTH_SHORT).show();
                        refrescartareas();
                        break;
                }
            }
        });
        builder.show();
    }
}
