package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.ui.home.HomeFragment;
import com.uv.app_plantae.MainActivity;

import org.w3c.dom.Text;

public class Perfil extends AppCompatActivity {

    /*
    private TextView mNombre, mCorreo, mPhone, mUser;
    private Button btn_Salir, btn_editarPerfil, btn_borrar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btn_Salir = (Button) findViewById(R.id.btn_CerrarSesion);
        btn_editarPerfil = (Button) findViewById(R.id.btn_editarPerfil);
        btn_borrar = (Button) findViewById(R.id.btn_eliminarPerfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Obtiene el id del usuario actual
        String id = mAuth.getCurrentUser().getUid();

        mNombre = (TextView) findViewById(R.id.Mostrar_nombre);
        mCorreo = (TextView) findViewById(R.id.Mostrar_correo);
        mPhone = (TextView) findViewById(R.id.Mostrar_telefono);
        mUser = (TextView) findViewById(R.id.Mostrar_usuario);

        mostrarUsuario();


            //Metodo para el boton de cerrar sesión
            btn_Salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                    startActivity(new Intent(Perfil.this, MainActivity.class));
                    finish();

                }
            });


        //Metodo para ir a la pantalla de editar perfil
        btn_editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Perfil.this, EditarPerfil.class));
            }
        });


        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPerfil();
            }
        });


    }


    private void mostrarUsuario(){

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String correo = snapshot.child("email").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String user = snapshot.child("user").getValue().toString();

                    mNombre.setText(name);
                    mCorreo.setText(correo);
                    mPhone.setText(phone);
                    mUser.setText(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void eliminarPerfil(){

        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String id_Padre = snapshot.getKey();
                    // Obtener referencia al padre
                    DatabaseReference padreRef = snapshot.getRef().getParent();

                    usersRef.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {



                            Toast.makeText(Perfil.this, "Perfl Eliminado", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            startActivity(new Intent(Perfil.this, MainActivity.class));
                            finish();
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //obtiene la instancia actual del usuario autenticado en Firebase Authentication.
        FirebaseUser user = mAuth.getCurrentUser();

        //Elimina el registro del usuario actual de Athentication
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Perfil.this, "Perfil eliminado con éxito", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/

}