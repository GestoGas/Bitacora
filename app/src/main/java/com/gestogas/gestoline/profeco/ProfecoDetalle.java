package com.gestogas.gestoline.profeco;

import static com.gestogas.gestoline.utils.Constantes.URL_SERVIDOR;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gestogas.gestoline.R;
import com.gestogas.gestoline.controllers.AppController;
import com.gestogas.gestoline.utils.Constantes;
import com.gestogas.gestoline.utils.DialogHelper;
import com.gestogas.gestoline.utils.DistanciaUtils;
import com.gestogas.gestoline.utils.TecladoUtils;
import com.gestogas.gestoline.utils.ToastUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfecoDetalle extends AppCompatActivity {
    int idPuesto, distMax = 0;
    String idProfeco;
    FloatingActionButton Cancelar;
    double latitudeEstacion = 0, longitudeEstacion = 0, latitudeEquipo = 0, longitudeEquipo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profeco_detalle);

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

        idProfeco  = getIntent().getStringExtra("idProfeco");

        Cancelar = findViewById(R.id.Cancelar);

        Detalle();

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
            Cancelar.setEnabled(false);
            Cancelar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_inactivo));
        }

        Cancelar.setOnClickListener(v-> {

            String mensaje = "Esta acción eliminara el evento PROFECO. ¿Desea continuar?";
            AlertWarning(mensaje);

        });

    }

    private void Detalle(){

        DialogHelper.showProgressDialog(this);
        String url = URL_SERVIDOR + "Dispensario/detalle-bitacora-dispensario.php?idBitacora=" + idProfeco;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        String folio = "", estado = "", fecha = "", hora= "" , nodispensario = "", marca = "",
                                modelo = "", serie = "", lado = "", producto = "", motivo = "", nombre = "", observaciones= "";


                        try {

                            // Recorre el JSONArray
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                folio = jsonObject.getString("folio");
                                estado = jsonObject.getString("estado");
                                fecha = jsonObject.getString("fecha");
                                hora = jsonObject.getString("hora");
                                nodispensario = jsonObject.getString("nodispensario");
                                marca = jsonObject.getString("marca");
                                modelo = jsonObject.getString("modelo");
                                serie = jsonObject.getString("serie");
                                lado = jsonObject.getString("lado");
                                producto = jsonObject.getString("producto");
                                motivo = jsonObject.getString("motivo");
                                nombre = jsonObject.getString("nombre");
                                observaciones = jsonObject.getString("observaciones");

                            }

                            TextView TxtFolio = findViewById(R.id.TxtFolio);
                            TextView TxtCancelada = findViewById(R.id.Cancelada);
                            TextView TxtFechaHora = findViewById(R.id.TxtFechaHora);
                            TextView TxtNoDispensario = findViewById(R.id.TxtNoDispensario);
                            TextView TxtMarca = findViewById(R.id.TxtMarca);
                            TextView TxtModelo = findViewById(R.id.TxtModelo);
                            TextView TxtSerie = findViewById(R.id.TxtSerie);
                            TextView TxtLado = findViewById(R.id.TxtLado);
                            TextView TxtProducto = findViewById(R.id.TxtProducto);
                            TextView TxtMotivo = findViewById(R.id.TxtMotivo);
                            TextView TxtResponsable = findViewById(R.id.TxtResponsable);
                            TextView TxtObservaciones = findViewById(R.id.TxtObservaciones);

                            TxtFolio.setText("00" + folio);
                            TxtFechaHora.setText(fecha + ", " + hora);
                            TxtNoDispensario.setText(nodispensario);
                            TxtMarca.setText(marca);
                            TxtModelo.setText(modelo);
                            TxtSerie.setText(serie);
                            TxtLado.setText(lado);
                            TxtProducto.setText(producto);
                            TxtMotivo.setText(motivo);
                            TxtResponsable.setText(nombre);
                            TxtObservaciones.setText(observaciones);

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
                DialogHelper.showProgressDialog(ProfecoDetalle.this);
                EliminarProfeco();

            }
        });
    }

    private void EliminarProfeco() {
        DialogHelper.showProgressDialog(this);
        String url = "Dispensario/cancelar-bitacora-dispensario.php";

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
                                ToastUtils.showAndThen(ProfecoDetalle.this, mensaje, ToastUtils.SUCCESS, () -> {
                                    setResult(RESULT_OK);
                                    finish();
                                });

                            }else{
                                DialogHelper.hideProgressDialog();
                                ToastUtils.show(ProfecoDetalle.this, mensaje, ToastUtils.ERROR);
                            }

                        }catch (Exception e){
                            ToastUtils.show(ProfecoDetalle.this, "Error: " + e.toString(), ToastUtils.INFO);
                            DialogHelper.hideProgressDialog();

                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtils.show(ProfecoDetalle.this, "Error: " + error.toString(), ToastUtils.INFO);
                        DialogHelper.hideProgressDialog();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idBitacora", idProfeco);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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