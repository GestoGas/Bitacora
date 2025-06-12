package com.gestogas.gestoline.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gestogas.gestoline.Home;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.controllers.AppController;
import com.gestogas.gestoline.data.dataUsuario;

import java.util.List;
public class adapterGerente extends RecyclerView.Adapter<adapterGerente.ItemViewHolder> {
    private final Context context;
    private final List<dataUsuario> dataList;
    public adapterGerente(Context context,List<dataUsuario> dataList){

        this.context = context;
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenido_lista_gerentes, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        dataUsuario item = dataList.get(position);
        final int idgerente = item.getId();
        final String nombre = item.getNombreusuario();

        holder.IdGerente.setText(Integer.toString(idgerente));
        holder.NombreCompleto.setText(nombre);

        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppController.getInstance().ActualizarUsuario(idgerente, nombre);
                Activity activity = (Activity) context;
                Intent actividad = new Intent(context, Home.class);
                activity.startActivity(actividad);
                activity.finish();

            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View ItemView;
        TextView IdGerente,NombreCompleto;
        public ItemViewHolder(@NonNull View view) {

            super(view);
            IdGerente = view.findViewById(R.id.IdGerente);
            NombreCompleto = view.findViewById(R.id.NombreCompleto);
            ItemView = view;

        }
    }
}
