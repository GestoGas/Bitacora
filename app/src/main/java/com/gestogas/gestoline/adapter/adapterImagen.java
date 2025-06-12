package com.gestogas.gestoline.adapter;

import static java.lang.System.load;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.data.dataImagen;
import com.gestogas.gestoline.utils.DialogHelper;
import com.gestogas.gestoline.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adapterImagen extends RecyclerView.Adapter<adapterImagen.ItemViewHolder>{
    private final Context context;
    private final List<dataImagen> dataList;
    private final String deleteUrl;
    private final OnEvidenciaEliminadaListener deleteListener;
    public adapterImagen(Context context, List<dataImagen> dataList, String deleteUrl, OnEvidenciaEliminadaListener deleteListener) {
        this.context = context;
        this.dataList = dataList;
        this.deleteUrl = deleteUrl;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contenido_imagen, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dataImagen item = dataList.get(position);

        Glide.with(holder.itemView.getContext()).
                load(item.getRuta()).
                into(holder.imageView);

        holder.deleteBtn.setOnClickListener(v -> {
            eliminarEvidencia(item.getId(), position);
        });

        holder.itemView.setOnClickListener(v -> {

            View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_image, null);
            ImageView imageDialog = dialogView.findViewById(R.id.imageDialog);

            Glide.with(context)
                    .load(item.getRuta())
                    .into(imageDialog);

            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setPositiveButton("Cerrar", null)
                    .show();

        });
    }

    @Override
    public int getItemCount() { return dataList.size(); }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        View ItemView;
        ImageView imageView;
        ImageButton deleteBtn;
        public ItemViewHolder(@NonNull View view) {
            super(view);

            imageView = view.findViewById(R.id.imageView);
            deleteBtn = view.findViewById(R.id.deleteBtn);

            ItemView = itemView;

        }
    }

    private void eliminarEvidencia(int id, int position) {
        DialogHelper.showProgressDialog((Activity) context);
        StringRequest request = new StringRequest(Request.Method.POST, deleteUrl,
                response -> {
                    dataList.remove(position);
                    notifyItemRemoved(position); // animación suave
                    notifyItemRangeChanged(position, dataList.size()); // ajusta el índice
                    deleteListener.onEvidenciaEliminada();
                    DialogHelper.hideProgressDialog();
                    ToastUtils.show((Activity) context, "Evidencia eliminada", ToastUtils.SUCCESS);


                },
                error -> ToastUtils.show((Activity) context, "Error al eliminar: " + error.getMessage(), ToastUtils.ERROR)
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    public interface OnItemDeleteListener {
        void onItemDeleted(int position);
    }

    public interface OnEvidenciaEliminadaListener {
        void onEvidenciaEliminada();
    }
}