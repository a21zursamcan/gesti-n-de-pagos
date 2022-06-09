package com.example.gestindegastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestindegastos.login.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //conseguimos la referencia a la firebase database desde la URL
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gestiongastos-63399-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView saldoTotal = findViewById(R.id.saldoTotal);
        Button añadirGasto=findViewById(R.id.añadirPago);

        añadirGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creando intent para abrir ModosDeJuego
                Intent intent = new Intent(MainActivity.this, anadirGastos.class);

                //abriendo ModosDeJuego
                startActivity(intent);

                //destruye esta actividad(MenuPrincipal)
                finish();
            }
        });

        databaseReference.child("usuarios").child(login.mAuth.getUid()).child("transacciones").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int suma=0;
                for(DataSnapshot conexiones : snapshot.child("tedeben").getChildren()){
                    suma=suma+conexiones.getValue().hashCode();
                }
                for(DataSnapshot conexiones : snapshot.child("debes").getChildren()){
                    suma=suma-conexiones.getValue().hashCode();
                }
                saldoTotal.setText(suma+"€");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}