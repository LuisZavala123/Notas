package com.example.notas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.R;
import com.example.notas.modelos.ModeloNota;
import com.example.notas.modelos.ModeloTarea;

import java.util.List;

public class AdapterTarea extends RecyclerView.Adapter<AdapterTarea.ViewHolder> {

    private Context context;
    private List<ModeloTarea> lista_tareas;

    longclick LongClick;

    public AdapterTarea(Context context, List listadetareas, longclick LongClick) {
        this.context = context;
        this.lista_tareas = listadetareas;
        this.LongClick = LongClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        public TextView nombre;
        public TextView descripcion;
        public longclick LongClick;

        public ViewHolder(View itemView, longclick LongClick) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombretarea);
            descripcion = itemView.findViewById(R.id.descripciontarea);
            this.LongClick = LongClick;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            this.LongClick.longclickTarea(getAdapterPosition());
            return true;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tareasola, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, LongClick);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(lista_tareas.get(position));
        ModeloTarea tarea = lista_tareas.get(position);
        holder.nombre.setText(tarea.getTitulo());
        holder.descripcion.setText(tarea.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista_tareas.size();
    }

    public interface longclick{
        void longclickTarea(int position);
    }

}
