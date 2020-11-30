package com.example.notas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.R;
import com.example.notas.modelos.ModeloNota;

import java.util.List;

public class AdapterNota extends RecyclerView.Adapter<AdapterNota.ViewHolder> {

    private Context context;
    private List<ModeloNota> lista_notas;

    longclick LongClick;

    public AdapterNota(Context context, List lista_notas, longclick LongClick) {
        this.context = context;
        this.lista_notas = lista_notas;
        this.LongClick = LongClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        public TextView nombre;
        public TextView descripcion;
        public longclick longClick;

        public ViewHolder(View itemView, longclick elClicLargo) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombrenota);
            descripcion = itemView.findViewById(R.id.descripcionnota);
            this.longClick = LongClick;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            this.longClick.longclick(getAdapterPosition());
            return true;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, LongClick);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(lista_notas.get(position));
        ModeloNota nota = lista_notas.get(position);
        holder.nombre.setText(nota.getTitulo());
        holder.descripcion.setText(nota.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return lista_notas.size();
    }

    public interface longclick{
        void longclick(int position);
    }

}
