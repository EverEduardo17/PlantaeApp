package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AgregarSitio extends AppCompatActivity {

    private TextView nombre, descripcion, plantas;
    private Button agregar;
    private String name = "";
    private String description = "";
    private String plant = "";

    FirebaseAuth mAuth;
    //Creamos objeto para base en realTime
    DatabaseReference mDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_sitio);

        //Instanciamos el objeto de auntenticación
        mAuth = FirebaseAuth.getInstance();
        //Instanciamos el objeto de Database RealTime
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nombre = findViewById(R.id.editTextText_nombre);
        descripcion = findViewById(R.id.editTextText_descripcion);
        plantas = findViewById(R.id.editTextText_plantas);

        agregar = findViewById(R.id.agregarS);


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                description = descripcion.getText().toString();
                plant = plantas.getText().toString();
                agregarSitio();
            }
        });

    }

    private void agregarSitio() {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", name);
        map.put("descripcion", description);
        map.put("plantas", plant);

        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference sitiosRef = mDatabase.child("Users").child(id).child("Sitios").push();

        sitiosRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AgregarSitio.this, "Sitio agregado exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AgregarSitio.this, Sitios.class));
                    finish();
                } else {
                    Toast.makeText(AgregarSitio.this, "ERROR: No fue posible crear los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}