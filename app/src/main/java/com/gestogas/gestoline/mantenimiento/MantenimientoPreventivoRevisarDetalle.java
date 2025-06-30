package com.gestogas.gestoline.mantenimiento;

import static android.view.View.VISIBLE;
import static com.gestogas.gestoline.utils.Constantes.URL_HOST;
import static com.gestogas.gestoline.utils.Constantes.URL_SERVIDOR;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.gestogas.gestoline.Evidencias;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.adapter.adapterImagen;
import com.gestogas.gestoline.data.dataImagen;
import com.gestogas.gestoline.utils.DialogHelper;
import com.gestogas.gestoline.utils.TecladoUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoPreventivoRevisarDetalle extends AppCompatActivity implements adapterImagen.OnEvidenciaEliminadaListener{

    String idMantenimiento, NumeroEquipo, NombreEquipo, numVerificacion,estado;
    TextView TxtTitulo, TxtSublista1,TxtSublista2,TxtSublista3,TxtSublista4,TxtSublista5;
    FloatingActionButton Editar;
    Button Evidencia;
    private RecyclerView recyclerView;
    private adapterImagen adapter;
    private List<dataImagen> itemList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mantenimiento_preventivo_revisar_detalle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idMantenimiento = getIntent().getStringExtra("idMantenimiento");
        NumeroEquipo = getIntent().getStringExtra("NumeroEquipo");
        NombreEquipo = getIntent().getStringExtra("NombreEquipo");
        numVerificacion = getIntent().getStringExtra("numVerificacion");
        estado = getIntent().getStringExtra("estado");

        TxtTitulo = findViewById(R.id.TxtTitulo);
        TxtTitulo.setText(NombreEquipo);

        TxtSublista1 = findViewById(R.id.TxtSublista1);
        TxtSublista2 = findViewById(R.id.TxtSublista2);
        TxtSublista3 = findViewById(R.id.TxtSublista3);
        TxtSublista4 = findViewById(R.id.TxtSublista4);
        TxtSublista5 = findViewById(R.id.TxtSublista5);
        Editar = findViewById(R.id.Editar);
        Evidencia = findViewById(R.id.Evidencia);

        String rutadelete = URL_SERVIDOR + "Mantenimiento/eliminar-evidencia-preventivo.php";
        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new adapterImagen(this,itemList,rutadelete,this);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        switch (NumeroEquipo) {
            case "2":
                TxtSublista1.setVisibility(VISIBLE);
                TxtSublista1.setText("Registros");
                TxtSublista2.setVisibility(VISIBLE);
                TxtSublista2.setText("Boquillas");
                break;
            case "33":
                TxtSublista1.setVisibility(VISIBLE);
                TxtSublista1.setText("Anuncio estructural y faldón");
                TxtSublista3.setVisibility(VISIBLE);
                TxtSublista3.setText("Avisos verticales");
                TxtSublista4.setVisibility(VISIBLE);
                TxtSublista4.setText("Marcaje horizontal");
                break;
            case "42":
                TxtSublista1.setVisibility(VISIBLE);
                TxtSublista1.setText("Tinacos");
                TxtSublista5.setVisibility(VISIBLE);
                TxtSublista5.setText("Cisterna");
                break;
        }

        Editar.setOnClickListener(v -> {

            Intent didactic = new Intent(this, MantenimientoPreventivoRevisar.class);

            didactic.putExtra("idMantenimiento", idMantenimiento);
            didactic.putExtra("NumeroEquipo", NumeroEquipo);
            didactic.putExtra("NombreEquipo", NombreEquipo);
            didactic.putExtra("numVerificacion", numVerificacion);
            didactic.putExtra("estado", estado);
            startActivityForResult(didactic, 1);

        });

        Evidencia.setOnClickListener(view -> {
            Intent home = new Intent(getApplicationContext(), Evidencias.class);
            home.putExtra("id", idMantenimiento);
            home.putExtra("categoria", "MantenimientoPreventivo");
            home.putExtra("carpeta", "Mantenimiento");
            home.putExtra("titulo", "Evidencia Mantenimiento Preventivo");
            startActivityForResult(home, 1);
            setResult(RESULT_OK);
        });

        fechtMantenimiento();

    }

    public void fechtMantenimiento() {

        DialogHelper.showProgressDialog(this);
        String url = URL_SERVIDOR + "Mantenimiento/mantenimiento-preventivo-detalle.php?idMantenimiento=" + idMantenimiento;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Datos generales del mantenimiento
                            int idmantenimiento = response.getInt("idmantenimiento");
                            String folio = response.getString("folio");
                            int idEstacion = response.getInt("id_estacion");
                            int idEquipo = response.getInt("id_equipo");
                            String nomMantenimiento = response.getString("nom_mantenimiento");
                            String fechaCreacion = response.getString("fechacreacion");
                            String observaciones = response.getString("observaciones");

                            String IDFPR = response.getString("IDFPR");
                            String IDFPS = response.getString("IDFPS");
                            String FPR = response.getString("FPR");
                            String FPS = response.getString("FPS");
                            String PFPR = response.getString("PFPR");
                            String PFPS = response.getString("PFPS");

                            TextView TxtFolio = findViewById(R.id.TxtFolio);
                            TextView txtobservaciones = findViewById(R.id.TxtObservaciones);
                            TextView TxtFechaHora = findViewById(R.id.TxtFechaHora);

                            TxtFolio.setText(folio);
                            txtobservaciones.setText(observaciones);
                            TxtFechaHora.setText(fechaCreacion);

                            JSONArray detallesArray = response.getJSONArray("detalles");

                            for (int i = 0; i < detallesArray.length(); i++) {
                                JSONObject detalle = detallesArray.getJSONObject(i);

                                int idDetalle = detalle.getInt("iddetalle");
                                int numList = detalle.getInt("num_list"); // ← el número que usarás
                                String nomActividad = detalle.getString("nom_actividad");
                                String resultado = detalle.getString("resultado");

                                int Constraint = getResources().getIdentifier("Constraint" + numList, "id", getPackageName());
                                int txtId = getResources().getIdentifier("TxtVerificar" + numList, "id", getPackageName());
                                int resultadoId = getResources().getIdentifier("TxtResultado" + numList, "id", getPackageName());

                                ConstraintLayout constraintLayout = findViewById(Constraint);
                                TextView txtVerificar = findViewById(txtId);
                                TextView txtResultado = findViewById(resultadoId);

                               if (txtVerificar != null && txtResultado != null) {

                                   if (nomActividad.trim().isEmpty()) {
                                       constraintLayout.setVisibility(View.GONE);
                                       txtVerificar.setVisibility(View.GONE);
                                       txtResultado.setVisibility(View.GONE);
                                   } else {
                                       constraintLayout.setVisibility(VISIBLE);
                                       txtVerificar.setVisibility(VISIBLE);
                                       txtResultado.setVisibility(VISIBLE);

                                       txtVerificar.setText(nomActividad);
                                       txtResultado.setText(resultado);
                                   }

                                }

                            }

                            ImageView ImagePR = findViewById(R.id.ImagePR);
                            ImageView ImagePS = findViewById(R.id.ImagePS);

                            TextView NomPR = findViewById(R.id.NomPR);
                            TextView NomPS = findViewById(R.id.NomPS);

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

                            NomPR.setText(PFPR);
                            NomPS.setText(PFPS);

                            DialogHelper.hideProgressDialog();

                        } catch (JSONException e) {
                            DialogHelper.hideProgressDialog();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DialogHelper.hideProgressDialog();
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchEvidencia(){

        DialogHelper.showProgressDialog(this);
        String ruta = URL_SERVIDOR + "Mantenimiento/lista-evidencia-preventivo.php?id=" + idMantenimiento;

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
    public void onEvidenciaEliminada() {
        fetchEvidencia();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            DialogHelper.showProgressDialog(this);
            fechtMantenimiento();
            fetchEvidencia();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        TecladoUtils.handleTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}