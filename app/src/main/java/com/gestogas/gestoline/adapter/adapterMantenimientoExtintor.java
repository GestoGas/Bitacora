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

import com.gestogas.gestoline.R;
import com.gestogas.gestoline.data.dataExtintores;

import java.util.List;

public class adapterMantenimientoExtintor extends RecyclerView.Adapter<adapterMantenimientoExtintor.ItemViewHolder>{
    private final Context context;
    private final List<dataExtintores> dataList;
    public adapterMantenimientoExtintor(Context context, List<dataExtintores> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenido_lista_mantenimiento_extintor, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dataExtintores item = dataList.get(position);

        holder.NuExtintor.setText(item.getNoextintor());
        holder.TipoExtintor.setText(item.getTipoextintor());
        holder.Ubicacion.setText(item.getUbicacion());
        holder.PesoKg.setText(item.getPesokg());

        holder.TxtFecha.setText(item.getUltimarecarga());
        holder.TxtManometro.setText(item.getManometro());
        holder.TxtBoquillaDescarga.setText(item.getBoquilladescarga());
        holder.TxtManguera.setText(item.getManguera());
        holder.TxtFuncionalidad.setText(item.getFuncionalidad());
        holder.TxtObservaciones.setText(item.getObservaciones());

    }

    @Override
    public int getItemCount() { return dataList.size(); }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View ItemView;
        TextView NuExtintor, TipoExtintor, Ubicacion, PesoKg;
        TextView TxtFecha, TxtManometro, TxtBoquillaDescarga,TxtManguera, TxtFuncionalidad, TxtObservaciones;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            TipoExtintor = view.findViewById(R.id.TipoExtintor);
            NuExtintor = view.findViewById(R.id.NuExtintor);
            Ubicacion = view.findViewById(R.id.Ubicacion);
            PesoKg = view.findViewById(R.id.PesoKg);

            TxtFecha = view.findViewById(R.id.TxtFecha);
            TxtManometro = view.findViewById(R.id.TxtManometro);
            TxtBoquillaDescarga = view.findViewById(R.id.TxtBoquillaDescarga);
            TxtManguera = view.findViewById(R.id.TxtManguera);
            TxtFuncionalidad = view.findViewById(R.id.TxtFuncionalidad);
            TxtObservaciones = view.findViewById(R.id.TxtObservaciones);


            ItemView = itemView;

        }
    }
}
