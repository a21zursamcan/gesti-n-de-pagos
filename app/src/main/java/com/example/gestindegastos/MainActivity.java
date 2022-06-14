package com.example.gestindegastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //conseguimos la referencia a la firebase database desde la URL
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gestiongastos-63399-default-rtdb.firebaseio.com/");

    public static ArrayList<Contacto>usuariosAMostrar=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView saldoTotal = findViewById(R.id.saldoTotal);
        Button añadirGasto=findViewById(R.id.añadirPago);
        Button cerrarSesion=findViewById(R.id.cerrarSesion);
        Button añadirContacto=findViewById(R.id.añadirContacto);

        añadirGasto.setOnClickListener(new View.OnClickListener() {
            boolean x=false;
            @Override
            public void onClick(View v) {
                databaseReference.child("usuarios").child(login.mAuth.getUid()).child("listaContactos").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        x = snapshot.exists();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (x){
                    //creando intent para abrir ModosDeJuego
                    Intent intent = new Intent(MainActivity.this, anadirGastos.class);

                    //abriendo ModosDeJuego
                    startActivity(intent);

                    //destruye esta actividad(MenuPrincipal)
                    finish();
                }
            }
        });

        databaseReference.child("usuarios").child(login.mAuth.getUid()).child("listaContactos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuariosAMostrar=new ArrayList<>();
                for(DataSnapshot conexiones : snapshot.getChildren()){



                    int suma=0;

                    try {
                        suma=suma+conexiones.child("tedebe").getValue().hashCode();

                    }catch (NullPointerException e){

                    }
                    try {
                        suma=suma-conexiones.child("debes").getValue().hashCode();

                    }catch (NullPointerException e){

                    }
                    String nombre="";
                    String ID=conexiones.getKey();
                    try {
                        nombre=conexiones.child("usuario").getValue().toString();
                    }catch (NullPointerException e){
                        login.obtenerUsuarioDeID(ID);
                    }


                    if(suma!=0){
                        usuariosAMostrar.add(new Contacto(nombre, ID, suma));
                    }
                }
                MainActivityAdapter listAdapter = new MainActivityAdapter(usuariosAMostrar,MainActivity.this);
                RecyclerView recyclerView = findViewById(R.id.rvUsuario);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.tipoDeUsuario=="registrado"){
                    login.cerrarSesion();
                    //creando intent para abrir login
                    Intent intent = new Intent(MainActivity.this, login.class);

                    //abriendo login
                    startActivity(intent);

                    //destruye esta actividad(MenuPrincipal)
                    finish();
                }
            }
        });

        añadirContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creando intent para abrir añadirContactos
                Intent intent = new Intent(MainActivity.this, AnadirContactos.class);

                //abriendo login
                startActivity(intent);

                //destruye esta actividad(MenuPrincipal)
                finish();
            }
        });

        databaseReference.child("usuarios").child(login.mAuth.getUid()).child("listaContactos").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int suma=0;
                for(DataSnapshot conexiones : snapshot.getChildren()){
                    try {
                        suma=suma+conexiones.child("tedebe").getValue().hashCode();

                    }catch (NullPointerException e){

                    }
                    try {
                        suma=suma-conexiones.child("debes").getValue().hashCode();

                    }catch (NullPointerException e){

                    }
                }
                saldoTotal.setText(suma+"€");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}