package com.gestogas.gestoline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.gestogas.gestoline.controllers.AppController;

public class Index extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(android.R.id.content);
        rootView.postDelayed(this::seleccionaVista, 1000);

    }

    private void seleccionaVista() {
        int sessionUsuario = AppController.getInstance().GetSession();

        Class<?> destinationActivity = (sessionUsuario == 1)
                ? Home.class
                : Login.class;


        Intent intent = new Intent(getApplicationContext(), destinationActivity);
        startActivity(intent);
        finish();
    }
}