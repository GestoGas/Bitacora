package com.gestogas.gestoline.adapter;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gestogas.gestoline.ExtintorCrearEditar;
import com.gestogas.gestoline.R;

import com.gestogas.gestoline.TanqueCrearEditar;
import com.gestogas.gestoline.data.dataTanques;

import java.util.List;

public class adapterTanques extends RecyclerView.Adapter<adapterTanques.ItemViewHolder>{
    private final Context context;
    private final List<dataTanques> dataList;
    public adapterTanques(Context context, List<dataTanques> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenido_lista_tanques, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dataTanques item = dataList.get(position);

        final int id = item.getId();
        final String notanque = item.getNotanque();
        final String capacidad = item.getCapacidad();
        final String producto = item.getProducto();

        holder.NoTanque.setText(notanque);
        holder.Capacidad.setText(capacidad);
        holder.Producto.setText(producto);

        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity activity = (Activity) context;
                Intent actividad = new Intent(context, TanqueCrearEditar.class);
                actividad.putExtra("estado","Editar");
                actividad.putExtra("idtanque", String.valueOf(id));
                actividad.putExtra("notanque", notanque);
                actividad.putExtra("capacidad", capacidad);
                actividad.putExtra("producto", producto);
                actividad.putExtra("titulo","Editar Tanque");
                actividad.putExtra("tituloboton","EDITAR TANQUE");
                activity.startActivityForResult(actividad,1);

            }
        });

    }

    @Override
    public int getItemCount() { return dataList.size(); }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View ItemView;
        TextView NoTanque, Capacidad, Producto;
        public ItemViewHolder(@NonNull View view) {
            super(view);

            NoTanque = view.findViewById(R.id.NoTanque);
            Capacidad = view.findViewById(R.id.Capacidad);
            Producto = view.findViewById(R.id.Producto);

            ItemView = itemView;

        }
    }
}
