package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditarSitio extends AppCompatActivity {
    private TextView nombre, descripcion, plantas;
    private Button editar;
    private String sitioId = "";
    private String nombreActual = "";
    private String descripcionActual = "";
    private String plantasActual = "";
    private DatabaseReference sitioRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sitio);

        nombre = findViewById(R.id.editTextText_nombre);
        descripcion = findViewById(R.id.editTextText_descripcion);
        plantas = findViewById(R.id.editTextText_plantas);
        editar = findViewById(R.id.editarS);

        // Obtener los datos del sitio a modificar desde el Intent
        sitioId = getIntent().getStringExtra("sitioId");
        nombreActual = getIntent().getStringExtra("nombre");
        descripcionActual = getIntent().getStringExtra("descripcion");
        plantasActual = getIntent().getStringExtra("plantas");

        // Mostrar los valores actuales en los campos correspondientes
        nombre.setText(nombreActual);
        descripcion.setText(descripcionActual);
        plantas.setText(plantasActual);

        // Obtener la referencia al sitio en la base de datos
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            sitioRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Sitios").child(sitioId);
        }

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(EditarSitio.this);
                alerta.setMessage("¿Desea modificar los datos?").setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Obtener los nuevos valores ingresados por el usuario
                                String nuevoNombre = nombre.getText().toString();
                                String nuevaDescripcion = descripcion.getText().toString();
                                String nuevasPlantas = plantas.getText().toString();

                                // Actualizar los valores en la base de datos
                                actualizarSitio(nuevoNombre, nuevaDescripcion, nuevasPlantas);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                alerta.show();
            }
        });
    }

    private void actualizarSitio(String nuevoNombre, String nuevaDescripcion, String nuevasPlantas) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", nuevoNombre);
        updates.put("descripcion", nuevaDescripcion);
        updates.put("plantas", nuevasPlantas);

        sitioRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditarSitio.this, "Sitio actualizado exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // Finalizar la actividad después de la actualización
                } else {
                    Toast.makeText(EditarSitio.this, "Error al actualizar el sitio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
