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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.databinding.FragmentVerPerfilBinding;
import com.uv.app_plantae.ui.home.HomeFragment;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class EditarPerfil extends AppCompatActivity {

    private EditText nombre, telefono, usuario, contrasena, correo, contrasena2;
    private TextView idPadre;
    private Button aceptar, cancelar;
    private String name = "";
    private String phone = "";
    private String user = "";
    private String password = "";
    private String password2 = "";
    private String email = "";

    //Creamos un objeto de autenticación
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Creamos objeto para base en realTime
    DatabaseReference mDatabase;

    //Optiene el id del usuario actual

    String id = mAuth.getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Asignamos variables a los elementos del layout
        nombre = (EditText) findViewById(R.id.nombreEdit);
        telefono = (EditText) findViewById(R.id.telefonoEdit);
        usuario = (EditText) findViewById(R.id.usuarioEdit);
        correo = (EditText) findViewById(R.id.correoEdit);
        contrasena = (EditText) findViewById(R.id.contrasenaEdit);
        contrasena2 = (EditText) findViewById(R.id.contrasenaEdit2);
        idPadre = (TextView) findViewById(R.id.Mostrar_idPadre);

        aceptar = (Button) findViewById(R.id.btn_aceptar);
        cancelar = (Button) findViewById(R.id.btn_cancelar);

        String id = mAuth.getCurrentUser().getUid();
        //Para establecer los valores actuales en el hind de los editText



        //Función del boton Registrar
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Las variabole serán igual a lo que ingrese el usuario
                name = nombre.getText().toString();
                phone = telefono.getText().toString();
                user = usuario.getText().toString();
                password = contrasena.getText().toString();
                email = correo.getText().toString();
                password2 = contrasena2.getText().toString();


                mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            String nombre = snapshot.child("name").getValue().toString();
                            String correo = snapshot.child("email").getValue().toString();
                            String telefono = snapshot.child("phone").getValue().toString();
                            String usuario = snapshot.child("user").getValue().toString();
                            String contrasena = snapshot.child("password").getValue().toString();

                            editarPerfil();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditarPerfil.this, FragmentVerPerfilBinding.class));
                finish();
            }
        });
    }

    private void verificarContrasena(){

    }

    private void editarPerfil(){
        Map<String, Object> personaMap = new HashMap<>();

        //Solo cambia los datos si NO estan vacios
        if (!name.isEmpty()) {
            personaMap.put("name", name);
        }
        if (!email.isEmpty()) {
            personaMap.put("email", email);
        }
        if (!phone.isEmpty()) {
            personaMap.put("phone", phone);
        }
        if (!user.isEmpty()) {
            personaMap.put("user", user);
        }
        if (!password2.isEmpty()) {
            personaMap.put("password", password2);
        }

        mDatabase.child("Users").child(id).updateChildren(personaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EditarPerfil.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditarPerfil.this, HomeFragment.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditarPerfil.this, "ERROR: No se pudieron actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }



}