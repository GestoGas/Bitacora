package com.gestogas.gestoline.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gestogas.gestoline.DiffUtil.PersonalAutorizadoDiff;
import com.gestogas.gestoline.DiffUtil.RecepcionBitacoraDiff;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.TanqueCrearEditar;
import com.gestogas.gestoline.data.dataPersonal;
import com.gestogas.gestoline.data.dataRecepcionBitacora;
import com.gestogas.gestoline.data.dataTanques;

import java.util.ArrayList;
import java.util.List;

public class adapterPersonal extends RecyclerView.Adapter<adapterPersonal.ItemViewHolder>{
    private final Context context;
    private final List<dataPersonal> dataList;
    private final List<dataPersonal> searchlList;

    public adapterPersonal(Context context, List<dataPersonal> dataList) {
        this.context = context;
        this.dataList = new ArrayList<>(dataList);
        this.searchlList = new ArrayList<>(dataList);

    }

    public void filter(String text) {
        text = text.toLowerCase().trim();
        dataList.clear();

        if (text.isEmpty()) {
            dataList.addAll(searchlList);
        } else {
            for (dataPersonal item : searchlList) {
                if (
                        item.getNombreusuario().toLowerCase().contains(text)
                ) {
                    dataList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateData(List<dataPersonal> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new PersonalAutorizadoDiff(this.dataList, newData)
        );
        this.dataList.clear();
        this.dataList.addAll(newData);

        diffResult.dispatchUpdatesTo(this);

        searchlList.clear();
        searchlList.addAll(newData);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenido_lista_personal_autorizado, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dataPersonal item = dataList.get(position);

        final int idautorizacion = item.getIdfirma();
        final String nombre = item.getNombreusuario();
        final String categoria = item.getCategoria();

        String autorizacion = (categoria.equals("RDP"))? "Recepción y Descarga" : "Mantenimiento";
        holder.NombreCompleto.setText(nombre);
        holder.Autorizacion.setText(autorizacion);

        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity activity = (Activity) context;

            }
        });

    }

    @Override
    public int getItemCount() { return dataList.size(); }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View ItemView;

        TextView NombreCompleto, Autorizacion;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            NombreCompleto = view.findViewById(R.id.NombreCompleto);
            Autorizacion = view.findViewById(R.id.Autorizacion);

            ItemView = view;

        }
    }

}
