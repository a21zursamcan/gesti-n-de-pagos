package com.example.gestindegastos.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gestindegastos.Contacto;
import com.example.gestindegastos.MainActivity;
import com.example.gestindegastos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;


public class login extends AppCompatActivity {
    public static String nombreUsuario="demo";
    public static String tipoDeUsuario;
    public static ArrayList<Contacto> listaContactosExistentes;
    public static String rUsuarioID="";

    //conseguimos la referencia a la firebase database desde la URL
    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gestiongastos-63399-default-rtdb.firebaseio.com/");

    public static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(login.this, MainActivity.class);

            //abriendo MenuPrincipal
            startActivity(intent);

//            intent.putExtra("registrado", tipoDeUsuario);
            tipoDeUsuario="registrado";

            databaseReference.child("usuarios").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    =FirebaseDatabase.getInstance().getReference("usuarios").child(mAuth.getUid()).child("usuario").getRef().;
                    nombreUsuario=snapshot.child(mAuth.getUid()).child("usuario").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //destruye esta actividad(login)
            revisionUsuariosExistentes();
            finish();
            return;
        }

        final AppCompatButton registrarse = findViewById(R.id.register);
        final AppCompatButton login = findViewById(R.id.login);
        final ImageView logoIV=findViewById(R.id.logo);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creando intent para abrir adminMenu
                Intent intent = new Intent(login.this, Register.class);

                //abriendo adminMenu
                startActivity(intent);

                //destruye esta actividad(login)
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });


    }

    public static void agregarUsuarioListaContactos(String nombre,String ID){
        databaseReference.child("usuarios").child(mAuth.getUid()).child("listaContactos").child(ID).child("usuario").setValue(nombre);
        databaseReference.child("usuarios").child(mAuth.getUid()).child("listaContactos").child(ID).child("tedebe").setValue(0);
        databaseReference.child("usuarios").child(mAuth.getUid()).child("listaContactos").child(ID).child("debes").setValue(0);
        databaseReference.child("usuarios").child(ID).child("listaContactos").child(mAuth.getUid()).child("usuario").setValue(nombreUsuario);
        databaseReference.child("usuarios").child(ID).child("listaContactos").child(mAuth.getUid()).child("tedebe").setValue(0);
        databaseReference.child("usuarios").child(ID).child("listaContactos").child(mAuth.getUid()).child("debes").setValue(0);
    }

    public void revisionUsuariosExistentes(){
        databaseReference.child("usuarios").child(mAuth.getUid()).child("listaContactos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaContactosExistentes=new ArrayList<>();

                //Obtenemos lista de contactos existentes
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    try{
                        String nombreUsuario=dataSnapshot.child("usuario").getValue().toString();
                        String idUsuario=dataSnapshot.getKey();
                        if(nombreUsuario!=null||idUsuario!=null){
                            listaContactosExistentes.add(new Contacto(nombreUsuario,idUsuario));
                        }
                    }catch (java.lang.NullPointerException e){
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void authenticateUser() {
        EditText etUser = findViewById(R.id.user);
        EditText etPassword = findViewById(R.id.password);

        String userString = etUser.getText().toString();
        String passwordString = etPassword.getText().toString();

        if (userString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, "Escribe usuario y contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(userString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(login.this, MainActivity.class);

                            //abriendo MenuPrincipal
                            startActivity(intent);

//                            intent.putExtra("registrado", tipoDeUsuario);

                            tipoDeUsuario = "registrado";
                            nombreUsuario=userString;

                            //destruye esta actividad(login)
                            finish();
                        } else {
                            Toast.makeText(login.this, "Autentificación fallada.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void cerrarSesion(){
        tipoDeUsuario="";
        nombreUsuario="";
        mAuth.signOut();
    }

    public static void obtenerUsuarioDeID(String ID) {
        databaseReference.child("usuarios").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rUsuarioID = snapshot.child("usuario").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}