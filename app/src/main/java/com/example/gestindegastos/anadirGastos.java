package com.example.gestindegastos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class anadirGastos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_gastos);
        Button añadirGasto=findViewById(R.id.añadirPago);

        añadirGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creando intent para abrir ModosDeJuego
                Intent intent = new Intent(anadirGastos.this, MainActivity.class);

                //abriendo ModosDeJuego
                startActivity(intent);

                //destruye esta actividad(MenuPrincipal)
                finish();
            }
        });
    }
}