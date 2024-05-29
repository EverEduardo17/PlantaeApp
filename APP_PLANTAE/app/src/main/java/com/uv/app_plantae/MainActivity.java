package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText contrasena;
    private Button btn_IniciarSesion;


    private String email;
    private String password;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        usuario = (EditText) findViewById(R.id.correoLog);
        contrasena = (EditText) findViewById(R.id.contrasenaLog);
        btn_IniciarSesion = (Button) findViewById(R.id.btn_IniciarSesion);


        btn_IniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = usuario.getText().toString();
                password = contrasena.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Campos vacios", Toast.LENGTH_SHORT).show();
                }else {
                    iniciarSesion();
                }
            }
        });

    }

    public void iniciarSesion() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Barra_menu.class));
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Metodo para mantaner la sesión iniciada (pensar si dejarlo o no)
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, Barra_menu.class));
            finish();
        }
    }

    public void irRegistro(View v){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    public void irMenu(View v){
        Intent i = new Intent(this, Barra_menu.class);
        startActivity(i);

    }


}