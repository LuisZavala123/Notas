package com.example.notas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.R;
import com.example.notas.modelos.ModeloMultimedia;

import java.io.File;
import java.util.ArrayList;

public class AdapterMultimedia extends RecyclerView.Adapter<AdapterMultimedia.ViewHolder>{

    public interface MultimediaClic {
        void ClicMulti(int position);
    }

    private MultimediaClic mOnMultimediaClickListener;

    private ArrayList<ModeloMultimedia> ListaMedios;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView desc;
        public ImageView imagen;

        MultimediaClic multimediaClic;

        public ViewHolder(View view, MultimediaClic multimediaClic) {
            super(view);
            desc = view.findViewById(R.id.multiDescripcion);
            imagen = view.findViewById(R.id.multiImagen);

            this.multimediaClic = multimediaClic;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            multimediaClic.ClicMulti(getAdapterPosition());
        }

    }

    public AdapterMultimedia(ArrayList<ModeloMultimedia> listaMedios, MultimediaClic multimediaClick) {
        this.ListaMedios = listaMedios;
        mOnMultimediaClickListener = multimediaClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.multimediasolo, parent, false);
        return new ViewHolder(itemView, mOnMultimediaClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModeloMultimedia medio = ListaMedios.get(position);
        holder.desc.setText(medio.descripcion);
        String ruta = medio.ruta;
        File imagen = new File(ruta);
        if(medio.getTipo().equals("foto")){
            holder.imagen.setImageResource(R.drawable.ic_photo);
        }else if(medio.getTipo().equals("video")){
            holder.imagen.setImageResource(R.drawable.ic_video);
        } else if(medio.getTipo().equals("imagen")){
            holder.imagen.setImageResource(R.drawable.ic_image);
        }else if(medio.getTipo().equals("galeria")){
            holder.imagen.setImageResource(R.drawable.ic_gallery);
        }else if(medio.getTipo().equals("audio")){
            holder.imagen.setImageResource(R.drawable.ic_audio);
        }
    }

    @Override
    public int getItemCount() {
        return ListaMedios.size();
    }

}
