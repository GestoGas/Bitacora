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

import com.gestogas.gestoline.DiffUtil.MantenimeintoDiff;
import com.gestogas.gestoline.mantenimiento.MantenimientoCorrectivoDetalle;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.data.dataMantenimiento;
import com.gestogas.gestoline.mantenimiento.MantenimientoPreventivoDos;

import java.util.ArrayList;
import java.util.List;

public class adapterMantenimeinto extends RecyclerView.Adapter<adapterMantenimeinto.ItemViewHolder>{
    private final Context context;
    private final List<dataMantenimiento> dataList;
    private final List<dataMantenimiento> searchlList;

    public adapterMantenimeinto(Context context, List<dataMantenimiento> dataList) {
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
            for (dataMantenimiento item : searchlList) {
                if (
                        item.getDescripcion().toLowerCase().contains(text) ||
                                item.getFolio().toLowerCase().contains(text) ||
                                item.getFecha().toLowerCase().contains(text) ||
                                item.getHora().toLowerCase().contains(text)
                ) {
                    dataList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateData(List<dataMantenimiento> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new MantenimeintoDiff(this.dataList, newData)
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenido_lista_mantenimiento, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dataMantenimiento item = dataList.get(position);

        int id = item.getId();
        String idequipo = item.getIdequipo();
        String numVerificacion = item.getNumverificacion();
        String descripcion = item.getDescripcion();

        holder.TxtFolio.setText(item.getFolio());
        holder.TxtDescripcion.setText(item.getDescripcion());
        holder.TxtFechaHora.setText(item.getFecha()+ ", " + item.getHora());

        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(idequipo.equals("0")) {

                    Activity activity = (Activity) context;
                    Intent didactic = new Intent(context, MantenimientoCorrectivoDetalle.class);
                    didactic.putExtra("idMantenimiento", String.valueOf(id));
                    activity.startActivityForResult(didactic, 1);

                }else{

                    if(numVerificacion.equals("1") || numVerificacion.equals("2")){

                        Activity activity = (Activity) context;
                        Intent didactic = new Intent(context, MantenimientoPreventivoDos.class);
                        didactic.putExtra("idMantenimiento", String.valueOf(id));
                        didactic.putExtra("NumeroEquipo", idequipo);
                        didactic.putExtra("NombreEquipo", descripcion);
                        didactic.putExtra("numVerificacion", numVerificacion);
                        activity.startActivityForResult(didactic, 1);

                    }

                }

            }
        });

    }

    @Override
    public int getItemCount() { return dataList.size(); }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View ItemView;
        TextView TxtFolio, TxtDescripcion, TxtFechaHora;
        public ItemViewHolder(@NonNull View view) {
            super(view);

            TxtFolio = view.findViewById(R.id.TxtFolio);
            TxtDescripcion = view.findViewById(R.id.TxtDescripcion);
            TxtFechaHora = view.findViewById(R.id.TxtFechaHora);

            ItemView = itemView;

        }
    }
}