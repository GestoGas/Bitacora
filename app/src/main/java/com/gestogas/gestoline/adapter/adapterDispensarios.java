package com.gestogas.gestoline.adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gestogas.gestoline.DiffUtil.DispensariosDiff;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.data.dataDispensarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adapterDispensarios extends RecyclerView.Adapter<adapterDispensarios.ItemViewHolder>{
    private final Context context;
    private final List<dataDispensarios> dataList;
    private final List<dataDispensarios> searchlList;

    public adapterDispensarios(Context context, List<dataDispensarios> dataList) {
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
            for (dataDispensarios item : searchlList) {
                if (
                               item.getNodispensario().toLowerCase().contains(text) ||
                                       item.getMarca().toLowerCase().contains(text) ||
                                       item.getModelo().toLowerCase().contains(text) ||
                                       item.getSerie().toLowerCase().contains(text)
                ) {
                    dataList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateData(List<dataDispensarios> newData) {

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new DispensariosDiff(this.dataList, newData)
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenido_lista_dispensarios, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dataDispensarios item = dataList.get(position);

        holder.nodispensario.setText(item.getNodispensario());
        holder.marca.setText(item.getMarca());
        holder.modelo.setText(item.getModelo());
        holder.serie.setText(item.getSerie());

        holder.nomproducto1.setText(item.getNomproducto1());
        holder.producto1.setText(item.getProducto1());

        String colorP1 = productoColores.getOrDefault(item.getNomproducto1(), "#000000");
        holder.nomproducto1.setTextColor(Color.parseColor(colorP1));

        holder.nomproducto2.setText(item.getNomproducto2());
        holder.producto2.setText(item.getProducto2());

        String colorP2 = productoColores.getOrDefault(item.getNomproducto2(), "#000000");
        holder.nomproducto2.setTextColor(Color.parseColor(colorP2));

        if (item.getNomproducto3().isEmpty()){

            holder.nomproducto3.setVisibility(View.VISIBLE);
            holder.producto3.setVisibility(View.VISIBLE);
            holder.nomproducto3.setText(item.getNomproducto3());
            holder.producto3.setText(item.getProducto3());
            String colorP3 = productoColores.getOrDefault(item.getNomproducto3(), "#000000");
            holder.nomproducto3.setTextColor(Color.parseColor(colorP3));

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View ItemView;
        TextView nodispensario, marca, modelo, serie, nomproducto1, producto1, nomproducto2, producto2, nomproducto3, producto3;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            nodispensario = view.findViewById(R.id.NoDispensario);
            marca = view.findViewById(R.id.Marca);
            modelo = view.findViewById(R.id.Modelo);
            serie = view.findViewById(R.id.Serie);
            nomproducto1 = view.findViewById(R.id.NomProducto1);
            producto1 = view.findViewById(R.id.Producto1);
            nomproducto2 = view.findViewById(R.id.NomProducto2);
            producto2 = view.findViewById(R.id.Producto2);
            nomproducto3 = view.findViewById(R.id.NomProducto3);
            producto3 = view.findViewById(R.id.Producto3);

            ItemView = view;

        }
    }

    private static final Map<String, String> productoColores = new HashMap<String, String>() {{
        put("BP REGULAR", "#4cd387");
        put("BP PREMIUM", "#f64c0f");
        put("EFITEC 87", "#4a8147");
        put("EFITEC 92", "#b94128");
        put("MAGNA", "#16BB43");
        put("PEMEX MAGNA", "#16BB43");
        put("PREMIUM", "#BB1616");
        put("PEMEX PREMIUM", "#BB1616");
        put("Shell Súper Regular", "#f7cc04");
        put("V Power Premiun", "#e32f18");
        put("G SUPER", "#77bd1e");
        put("G PREMIUM", "#e11682");
        put("G DIESEL", "#5d0e8b");
        put("DIESEL", "#000000");
        put("PEMEX DIESEL", "#000000");
        put("Diésel", "#000000");
    }};
}

