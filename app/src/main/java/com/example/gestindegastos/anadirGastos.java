package com.example.gestindegastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestindegastos.anadirGastosAdapter;

import com.example.gestindegastos.login.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class anadirGastos extends AppCompatActivity {

    public static ArrayList<Contacto> contactos=new ArrayList<>();
    public static int DeudaTotal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_gastos);
        Button añadirGasto = findViewById(R.id.añadirPago);
        TextView deudaTV = findViewById(R.id.editTextTextPersonName2);

        anadirGastosAdapter listAdapter= new anadirGastosAdapter(login.listaContactosExistentes, anadirGastos.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(anadirGastos.this));
        recyclerView.setAdapter(listAdapter);

        añadirGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeudaTotal = Integer.valueOf(deudaTV.getText().toString());
                anadirGastosContactos();

                //creando intent para abrir el MainActivity
                Intent intent = new Intent(anadirGastos.this, MainActivity.class);

                //abriendo MainActivity
                startActivity(intent);

                //destruye esta actividad(anadirGastos)
                finish();
            }
        });
    }

    public static void anadirGastosContactos(){
        int DeudaPorContacto=DeudaTotal/(contactos.size()+1);
        for(Contacto contactoActivo : contactos){
            //Comprobamos si el usuario tiene deudas anteriores
            login.databaseReference.child("usuarios").child(login.mAuth.getUid()).child("listaContactos").child(contactoActivo.getID()).child("tedebe").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int DeudaTotal=snapshot.getValue().hashCode()+DeudaPorContacto;
                    snapshot.getRef().setValue(DeudaTotal);
                    login.databaseReference.child("usuarios").child(contactoActivo.ID).child("listaContactos").child(login.mAuth.getUid()).child("debes").setValue(DeudaPorContacto);
                    login.databaseReference.child("usuarios").child(login.mAuth.getUid()).child("listaContactos").child(contactoActivo.getID()).child("tedebe").removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}