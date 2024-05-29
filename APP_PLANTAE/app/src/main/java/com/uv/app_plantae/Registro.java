package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    //Declaramos los elementos utilizados en el Layout
    private EditText nombre, telefono, usuario, contrasena, correo;
    private Button registrarse;

    //Variable de los datos que se van a registrar
    private String name = "";
    private String phone = "";
    private String user = "";
    private String password = "";
    private String email = "";

    //Creamos un objeto de autenticación
    FirebaseAuth mAuth;
    //Creamos objeto para base en realTime
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Instanciamos el objeto de auntenticación
        mAuth = FirebaseAuth.getInstance();
        //Instanciamos el objeto de Database RealTime
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Referenciamos los elementos del Layout
        nombre = (EditText) findViewById(R.id.nombreReg);
        telefono = (EditText) findViewById(R.id.telefonoReg);
        usuario = (EditText) findViewById(R.id.usuarioReg);
        contrasena = (EditText) findViewById(R.id.contrasenaReg);
        correo = (EditText) findViewById(R.id.correoReg);
        registrarse = (Button) findViewById(R.id.btn_Registrarse);

        //Función del boton Registrar
        registrarse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                alerta.setMessage("¿Desea guardar los datos y crear una cuenta?").setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Las variabole serán igual a lo que ingrese el usuario
                                name = nombre.getText().toString();
                                phone = telefono.getText().toString();
                                user = usuario.getText().toString();
                                password = contrasena.getText().toString();
                                email = correo.getText().toString();

                                //Válida que ningun elemnto esté vacio
                                if (name.isEmpty() || phone.isEmpty() || user.isEmpty() || password.isEmpty() || email.isEmpty()){
                                    Toast.makeText(Registro.this, "Todos los campos deben llenarse", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //Válida que la contraseña tenga minio 6 caracteres
                                    if (password.length() >= 6){
                                        registrarUsuario();
                                    }else {
                                        Toast.makeText(Registro.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });


                alerta.show(); //agregar esta línea para mostrar el cuadro de diálogo
            }
        });

    }

    public void registrarUsuario(){

        //Método para crear un usuario con el correo y la contraseña
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            //Método para validar el usuario
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){ //Si se realiza la operación correctamente
                    //Crea un mapa con los datos del usuario
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("phone", phone);
                    map.put("user", user);
                    map.put("password", password);
                    map.put("email", email);
                    //Recupera el id del usuario
                    String id = mAuth.getCurrentUser().getUid();
                    //Crea los nodos hijos para ir estructurando la base de datos (si el nodo existe actualiza y si no lo crea)
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        //Crear la base en RealTime
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Toast.makeText(Registro.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registro.this, Barra_menu.class));
                                finish();
                            }else {
                                Toast.makeText(Registro.this, "ERROS: No fue posible crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(Registro.this, "ERROR: No fue posible registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}