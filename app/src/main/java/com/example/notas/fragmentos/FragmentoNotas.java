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
import com.example.notas.DAOS.DAONota;
import com.example.notas.R;
import com.example.notas.adapters.AdapterNota;
import com.example.notas.modelos.ModeloNota;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentoNotas extends Fragment implements AdapterNota.longclick {

    RecyclerView recycler;
    FloatingActionButton refrescarNotasManual;
    DAONota daoNota;

    public FragmentoNotas() {

    }

    ArrayList<ModeloNota> lista;
    RecyclerView.Adapter adaptador;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.listanotas, container, false);
        recycler = root.findViewById(R.id.listanotasrecycler);
        refrescarNotasManual =  root.findViewById(R.id.refreshNotas);
        //refrescarNotas
        recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        lista = new DAONota(getContext()).allNotas();
        adaptador = new AdapterNota(getContext(), lista, this);
        recycler.setAdapter(adaptador);
        refrescarNotasManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refrescar();
            }
        });
        return root;
    }
    public void refrescar(){
        recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        lista = new DAONota(getContext()).allNotas();
        adaptador = new AdapterNota(getContext(), lista, this);
        recycler.setAdapter(adaptador);
    }
    @Override
    public void longclick(final int position) {
        final CharSequence[] items = {"Editar", "Eliminar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: //Editar
                        Intent intent = new Intent(getContext(), Activity_agregar.class);
                        intent.putExtra("nota", lista.get(position));
                        startActivity(intent);
                        break;
                    case 1: //Eliminar
                        daoNota = new DAONota(getContext());
                        daoNota.eliminarNota(lista.get(position).getId());
                        Toast.makeText(getContext(), "Se eliminó el registro.", Toast.LENGTH_SHORT).show();
                        refrescar();
                        break;
                }
            }
        });
        builder.show();
    }
}
