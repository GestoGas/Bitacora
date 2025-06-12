package com.gestogas.gestoline;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gestogas.gestoline.controllers.AppController;
import com.gestogas.gestoline.utils.Constantes;
import com.gestogas.gestoline.utils.DialogHelper;
import com.gestogas.gestoline.utils.LocationHelper;
import com.gestogas.gestoline.utils.ToastUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ConfigurarUbicacion extends BaseActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationHelper locationHelper;
    private TextView LatitudLongitud;
    double latitudEstacion = 0;
    double longitudEstacion = 0;
    int Idestacion;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_ubicacion);

         DialogHelper.showProgressDialog(this);
         Idestacion = AppController.getInstance().GetIdestacion();
         LatitudLongitud = findViewById(R.id.LatitudLongitud);
         // Inicializa LocationHelper
         locationHelper = new LocationHelper(this);

         // Verifica permisos
         if (locationHelper.checkLocationPermissions()) {
             if (LocationHelper.isLocationEnabled(this)) {
                 startLocationUpdates();
             } else {
                 checkLocationSettings();
             }
         } else {
             locationHelper.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE);
         }

     }
    private void startLocationUpdates() {
        locationHelper.startLocationUpdates(new LocationHelper.LocationListener() {
            @Override
            public void onLocationReceived(double latitude, double longitude) {
                latitudEstacion = latitude;
                longitudEstacion = longitude;
                LatitudLongitud.setText("Latitud= " +  latitudEstacion + "\n" + "Longitud= " + longitudEstacion);
                DialogHelper.hideProgressDialog();
            }
        });
    }

    private void checkLocationSettings() {
        new MaterialAlertDialogBuilder(this) // Usa MaterialAlertDialogBuilder para un diseño moderno
                .setTitle("Localización desactivada") // Título desde strings.xml
                .setMessage("Su ubicación está desactivada.\\nPor favor active su ubicación en la configuración del dispositivo.") // Mensaje desde strings.xml
                .setPositiveButton("Configuración de ubicación", (dialog, which) -> {
                    // Abre la configuración de ubicación
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Cierra el diálogo sin hacer nada
                    dialog.dismiss();
                })
                .setCancelable(false) // Evita que el usuario cierre el diálogo tocando fuera de él
                .show(); // Muestra el diálogo
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                ToastUtils.show(this, "Permisos de ubicación denegados", ToastUtils.INFO);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationHelper.stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LocationHelper.isLocationEnabled(this)) {
            startLocationUpdates();
        }
    }

    public void btnCalibrar(View view){
        DialogHelper.showProgressDialog(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_SERVIDOR + Constantes.FOLDER_CONFI +"actualizar-ubicacion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonarray = new JSONArray(response);
                            int estado = 0;

                            if (jsonarray.length() > 0) {
                                JSONObject obj = jsonarray.getJSONObject(0);
                                estado = obj.getInt("estado");
                            }

                            if (estado == 1){

                                ToastUtils.showAndThen(ConfigurarUbicacion.this, "Se actualizo correctamente tu ubicación", ToastUtils.SUCCESS, () -> {
                                    AppController.getInstance().SetUbicacion();
                                    AppController.getInstance().SetLatitud(String.valueOf(latitudEstacion));
                                    AppController.getInstance().SetLongitud(String.valueOf(longitudEstacion));
                                    Intent home = new Intent(getApplicationContext(), Home.class);
                                    startActivityForResult(home, 0);
                                    finish();
                                });

                            }

                        }catch (JSONException e){
                            ToastUtils.show(ConfigurarUbicacion.this, "Se produjo un error, revise su conexión a internet", ToastUtils.INFO);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtils.show(ConfigurarUbicacion.this, "Se produjo un error, revise su conexión a internet", ToastUtils.INFO);
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idestacion", String.valueOf(Idestacion));
                params.put("latitudEstacion", String.valueOf(latitudEstacion));
                params.put("longitudEstacion", String.valueOf(longitudEstacion));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
