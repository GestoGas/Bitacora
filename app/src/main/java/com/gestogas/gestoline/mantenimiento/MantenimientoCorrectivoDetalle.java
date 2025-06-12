package com.gestogas.gestoline.mantenimiento;

import static com.gestogas.gestoline.utils.Constantes.URL_HOST;
import static com.gestogas.gestoline.utils.Constantes.URL_SERVIDOR;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.gestogas.gestoline.Evidencias;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.adapter.adapterImagen;
import com.gestogas.gestoline.controllers.AppController;
import com.gestogas.gestoline.data.dataImagen;
import com.gestogas.gestoline.utils.Constantes;
import com.gestogas.gestoline.utils.DialogHelper;
import com.gestogas.gestoline.utils.DistanciaUtils;
import com.gestogas.gestoline.utils.TecladoUtils;
import com.gestogas.gestoline.utils.ToastUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MantenimientoCorrectivoDetalle extends AppCompatActivity implements adapterImagen.OnEvidenciaEliminadaListener{

    int idPuesto, distMax = 0;
    String idMantenimiento;
    TextView TxtFolio,NombreEquipo, DescHallazgo, DescActividades, Herramientas, TxtFechaHora;
    ImageView ImagePR, ImagePS;
    private FloatingActionButton Eliminar, Editar;
    double latitudeEstacion = 0, longitudeEstacion = 0, latitudeEquipo = 0, longitudeEquipo = 0;
    Button Evidencia;

    private RecyclerView recyclerView;
    private adapterImagen adapter;
    private List<dataImagen> itemList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_correctivo_detalle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        idPuesto = AppController.getInstance().GetIdPuesto();
        latitudeEquipo = Double.parseDouble(AppController.getInstance().GetLatitudEquipo());
        longitudeEquipo = Double.parseDouble(AppController.getInstance().GetLongitudEquipo());
        distMax = Integer.parseInt(AppController.getInstance().GetDistMax());
        latitudeEstacion = Double.parseDouble(AppController.getInstance().GetLatitud());
        longitudeEstacion = Double.parseDouble(AppController.getInstance().GetLongitud());

        idMantenimiento = getIntent().getStringExtra("idMantenimiento");

        TxtFolio = findViewById(R.id.TxtFolio);
        NombreEquipo = findViewById(R.id.NombreEquipo);
        DescHallazgo = findViewById(R.id.DescHallazgo);
        DescActividades = findViewById(R.id.DescActividades);
        Herramientas = findViewById(R.id.Herramientas);
        TxtFechaHora = findViewById(R.id.TxtFechaHora);
        ImagePR = findViewById(R.id.ImagePR);
        ImagePS = findViewById(R.id.ImagePS);

        Eliminar = findViewById(R.id.Eliminar);
        Editar = findViewById(R.id.Editar);
        Evidencia = findViewById(R.id.Evidencia);

        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String rutadelete = URL_SERVIDOR + "Mantenimiento/eliminar-evidencia-correctivo.php";
        itemList = new ArrayList<>();
        adapter = new adapterImagen(this,itemList,rutadelete,this);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);


        Editar.setOnClickListener(v-> {
            Intent home = new Intent (this, MantenimientoCorrectivoCrearEditar.class);
            home.putExtra("estado","Editar");
            home.putExtra("idMantenimiento",idMantenimiento);
            home.putExtra("titulo","Editar Mantenimiento Correctivo");
            home.putExtra("tituloboton","EDITAR MANTENIMIENTO");
            startActivityForResult(home, 1);
        });

        Eliminar.setOnClickListener(v-> {
            String mensaje = "Esta acción eliminara el mantenimiento correctivo. ¿Desea continuar?";
            AlertWarning(mensaje);
        });

        fetchMantenimientoCorrectivo();
        fetchEvidencia();

        boolean validarDistancia = DistanciaUtils.validarDistancia(
                getApplicationContext(),
                idPuesto,
                latitudeEquipo,
                longitudeEquipo,
                latitudeEstacion,
                longitudeEstacion,
                distMax
        );


        if (!validarDistancia) {

            Editar.setEnabled(false);
            Editar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_inactivo));

            Eliminar.setEnabled(false);
            Eliminar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_inactivo));
        }

        Evidencia.setOnClickListener(view -> {
            Intent home = new Intent(getApplicationContext(), Evidencias.class);
            home.putExtra("id", idMantenimiento);
            home.putExtra("categoria", "MantenimientoCorrectivo");
            home.putExtra("carpeta", "Mantenimiento");
            home.putExtra("titulo", "Evidencia Mantenimiento Correctivo");
            startActivityForResult(home, 1);
            setResult(RESULT_OK);
        });
    }

    private void AlertWarning(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_warning,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        Button alertCancelar = view.findViewById(R.id.alertCancelar);
        Button alertAceptar = view.findViewById(R.id.alertAceptar);
        TextView Mensaje = view.findViewById(R.id.txtMensaje);

        Mensaje.setText(mensaje);

        alertCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        alertAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DialogHelper.showProgressDialog(MantenimientoCorrectivoDetalle.this);
                EliminarMantenimiento();

            }
        });
    }

    private void EliminarMantenimiento() {
        String url = "Mantenimiento/eliminar-mantenimiento-correctivo.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_SERVIDOR + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonarray = new JSONArray(response);
                            int estado = 0;
                            String mensaje = "";

                            if (jsonarray.length() > 0) {
                                JSONObject obj = jsonarray.getJSONObject(0);
                                estado = obj.getInt("estado");
                                mensaje = obj.getString("mensaje");
                            }

                            if (estado == 1){

                                DialogHelper.hideProgressDialog();
                                ToastUtils.showAndThen(MantenimientoCorrectivoDetalle.this, mensaje, ToastUtils.SUCCESS, () -> {
                                    setResult(RESULT_OK);
                                    finish();
                                });

                            }else{
                                DialogHelper.hideProgressDialog();
                                ToastUtils.show(MantenimientoCorrectivoDetalle.this, mensaje, ToastUtils.ERROR);
                            }

                        }catch (Exception e){
                            ToastUtils.show(MantenimientoCorrectivoDetalle.this, "Error: " + e.toString(), ToastUtils.INFO);
                            DialogHelper.hideProgressDialog();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtils.show(MantenimientoCorrectivoDetalle.this, "Error: " + error.toString(), ToastUtils.INFO);
                        DialogHelper.hideProgressDialog();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idMantenimiento", String.valueOf(idMantenimiento));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void fetchMantenimientoCorrectivo() {

        DialogHelper.showProgressDialog(this);
        String url = URL_SERVIDOR + "Mantenimiento/detalle-mantenimiento-correctivo.php?idMantenimiento=" + idMantenimiento;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        String folio = "", equipo = "", dhallazgo = "", dactividad = "", herramienta = "", fecha = "",
                                hora = "", FPR = "", FPS = "", PFPR = "", PFPS = "",
                                IDFPR = "", IDFPS = "";

                        try {

                            // Recorre el JSONArray
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                folio = jsonObject.getString("folio");
                                equipo = jsonObject.getString("equipo");
                                dhallazgo = jsonObject.getString("dhallazgo");
                                dactividad = jsonObject.getString("dactividad");
                                herramienta = jsonObject.getString("herramienta");
                                fecha = jsonObject.getString("fecha");
                                hora = jsonObject.getString("hora");
                                IDFPR = jsonObject.getString("IDFPR");
                                IDFPS = jsonObject.getString("IDFPS");
                                FPR = jsonObject.getString("FPR");
                                FPS = jsonObject.getString("FPS");
                                PFPR = jsonObject.getString("PFPR");
                                PFPS = jsonObject.getString("PFPS");

                            }

                            TxtFolio.setText(folio);
                            NombreEquipo.setText(equipo);
                            DescHallazgo.setText(dhallazgo);
                            DescActividades.setText(dactividad);
                            Herramientas.setText(herramienta);

                            TxtFechaHora.setText(fecha + ", " + hora);

                            TextView NomPR = findViewById(R.id.NomPR);
                            TextView NomPS = findViewById(R.id.NomPS);
                            NomPR.setText(PFPR);
                            NomPS.setText(PFPS);

                            if(IDFPR.equals("0")){
                                Glide.with(getApplicationContext()).load(URL_SERVIDOR + "Mantenimiento/ImagenFirma/" + FPR).into(ImagePR);
                            }else{
                                Glide.with(getApplicationContext()).load(URL_HOST + "imgs/firma-personal/" + FPR).into(ImagePR);
                            }

                            if(IDFPS.equals("0")){
                                Glide.with(getApplicationContext()).load(URL_SERVIDOR + "Mantenimiento/ImagenFirma/" + FPS).into(ImagePS);
                            }else{
                                Glide.with(getApplicationContext()).load(URL_HOST + "imgs/firma-personal/" + FPS).into(ImagePS);
                            }


                        } catch (JSONException e) {
                            DialogHelper.hideProgressDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogHelper.hideProgressDialog();
                    }
                });

        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onEvidenciaEliminada() {
        fetchEvidencia();
    }

    private void fetchEvidencia(){

        DialogHelper.showProgressDialog(this);
        String ruta = URL_SERVIDOR + "Mantenimiento/lista-evidencia-correctivo.php?id=" + idMantenimiento;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ruta, null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // Limpia la lista actual
                            itemList.clear();

                            // Recorre el JSONArray
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String ruta = jsonObject.getString("ruta");
                                String nombre = jsonObject.getString("nombre");

                                // Crea un objeto Item y lo añade a la lista
                                dataImagen item = new dataImagen(id, ruta, nombre);
                                itemList.add(item);
                            }

                            // Notifica al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();
                            DialogHelper.hideProgressDialog();


                        } catch (JSONException e) {
                            DialogHelper.hideProgressDialog();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        DialogHelper.hideProgressDialog();
                    }
                });

        requestQueue.add(jsonArrayRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            DialogHelper.showProgressDialog(this);
            fetchMantenimientoCorrectivo();
            fetchEvidencia();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        TecladoUtils.handleTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_OK);
        finish();
        return true;
    }

}