package com.example.gestindegastos.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestindegastos.MainActivity;
import com.example.gestindegastos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final AppCompatButton registroFin = findViewById(R.id.register);
        final AppCompatButton volverLogin = findViewById(R.id.iniciarSesion);
        registroFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        volverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creando intent para abrir adminMenu
                Intent intent = new Intent(Register.this, login.class);

                //abriendo adminMenu
                startActivity(intent);

                //destruye esta actividad(login)
                finish();
            }
        });
    }

    private void registerUser() {
        EditText user = findViewById(R.id.user);
        EditText correoElectronico = findViewById(R.id.correoElectronico);
        EditText password = findViewById(R.id.password);

        String userString = user.getText().toString();
        String emailString = correoElectronico.getText().toString();
        String passwordString = password.getText().toString();

        if (userString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos, por favor", Toast.LENGTH_LONG).show();
            return;
        }

        login.mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(userString, emailString, passwordString);
                            FirebaseDatabase.getInstance().getReference("usuarios")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //creando intent para abrir MenuPrincipal
                                            Intent intent = new Intent(Register.this, MainActivity.class);

                                            //abriendo MenuPrincipal
                                            startActivity(intent);

                                            //destruye esta actividad(login)
                                            finish();
                                        }
                                    });
                        } else {
                            Toast.makeText(Register.this, "Fallo en la Autentificación",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}