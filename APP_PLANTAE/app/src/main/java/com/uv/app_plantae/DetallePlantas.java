package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.actions.ItemListIntents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.pojo.Plantas;
import com.uv.app_plantae.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetallePlantas extends AppCompatActivity {

    private ImageView imgItemDetail;
    private TextView tvTituloDetail, tvDescripcionDetail, tvdescripcion;
    private Button btn_agregar;
    private Plantas itemDetail;

    BusquedaPlantas busquedaPlantas;

    private String cientifico = "";
    private String img = "";
    private String nombre = "";

    //Creamos un objeto de autenticacion
    FirebaseAuth mAuth;

    //Creamos un objeto de la base de datos
    DatabaseReference mDatabase;

    List<Plantas> plantasAgregadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_plantas);
        //Nos indica que es un activity diferente con el nombre de la clase
        setTitle(getClass().getSimpleName());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initViews();
        initValues();

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarMiPlanta();
            }
        });

    }

    private void initViews(){
        imgItemDetail = findViewById(R.id.imgItemDetails);
        tvTituloDetail = findViewById(R.id.TituloDetails);
        tvDescripcionDetail = findViewById(R.id.DescripcionDetails);
        tvdescripcion = findViewById(R.id.verDescripcion);
        btn_agregar = findViewById(R.id.btn_agregar);
    }

    private void initValues(){
        itemDetail = (Plantas) getIntent().getExtras().getSerializable("itemDetail");

        //Seteamos valores
        String url_img = itemDetail.getImg();
        Glide.with(this).load(url_img).into(imgItemDetail);
        tvTituloDetail.setText(itemDetail.getNombre());
        tvDescripcionDetail.setText(itemDetail.getCientifico());
        tvdescripcion.setText(itemDetail.getDescripcion());
    }

    private void registrarMiPlanta(){
        if (!plantasAgregadas.contains(itemDetail)) {
            // La planta no ha sido agregada antes, entonces la agregamos en la lista
            plantasAgregadas.add(itemDetail);
            //Creamos un mapa con los datos de la planta
            Map<String, Object> map = new HashMap<>();
            map.put("cientifico", itemDetail.getCientifico());
            map.put("img", itemDetail.getImg());
            map.put("nombre", itemDetail.getNombre());
            map.put("descripcion", itemDetail.getDescripcion());

            //Recuperamos el id del usuario actual
            String id = mAuth.getCurrentUser().getUid();

            mDatabase.child("Users").child(id).child("MisPlantas")
                    .orderByChild("nombre").equalTo(itemDetail.getNombre())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // La planta ya está registrada
                                Toast.makeText(DetallePlantas.this, "Esta planta ya está registrada", Toast.LENGTH_SHORT).show();
                            } else {
                                // La planta no está registrada, la agregamos
                                mDatabase.child("Users").child(id).child("MisPlantas").push().setValue(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(DetallePlantas.this, "Planta agregada", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(DetallePlantas.this, Barra_menu.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(DetallePlantas.this, "Error: No fue posible agregar la planta", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DetallePlantas.this, "Error: No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}