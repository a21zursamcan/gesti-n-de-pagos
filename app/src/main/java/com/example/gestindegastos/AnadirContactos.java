package com.example.gestindegastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gestindegastos.login.Register;
import com.example.gestindegastos.login.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnadirContactos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_contactos);
        EditText usuarioBuscado=findViewById(R.id.nombreUsuarioBuscado);
        boolean borrarListeners=false;
        Button Atras= findViewById(R.id.botonAtras);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creando intent para abrir MainActivity
                Intent intent = new Intent(AnadirContactos.this, MainActivity.class);

                //abriendo MainActivity
                startActivity(intent);

                //destruye esta actividad(AnadirContactos)
                finish();
            }
        });

        usuarioBuscado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Contacto> usuariosCoincidentes=new ArrayList();
                ArrayList<Contacto> contactosExistentes=new ArrayList();

                login.databaseReference.child("usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("usuario").getValue().toString().contains(s)){
                                if(dataSnapshot.child("usuario").getValue().toString().equalsIgnoreCase(login.nombreUsuario)){}else{
                                    //Comprobamos que el usuario no coincida con ninguno de los ya agregados
                                    boolean existente=false;
                                    if(login.listaContactosExistentes!=null){
                                        for (Contacto contactoExistente:login.listaContactosExistentes){
                                            if(contactoExistente.getNombre().equalsIgnoreCase(dataSnapshot.child("usuario").getValue().toString())){
                                                existente=true;
                                            }
                                        }
                                    }else{
                                        existente=false;
                                    }
                                    if(!existente){
                                        String nombreUsuario=dataSnapshot.child("usuario").getValue().toString();
                                        String idUsuario=dataSnapshot.getKey();
                                        usuariosCoincidentes.add(new Contacto(nombreUsuario,idUsuario));
                                    }
                                }
                            }
                        }
                        login.databaseReference.child("usuarios").removeEventListener(this);
                        AnadirContactosAdapter listAdapter = new AnadirContactosAdapter(usuariosCoincidentes,AnadirContactos.this);
                        RecyclerView recyclerView  = findViewById(R.id.listaContactos);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AnadirContactos.this));
                        recyclerView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
//        boton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
    }
}