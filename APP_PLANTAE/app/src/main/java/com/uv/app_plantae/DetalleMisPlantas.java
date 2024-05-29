package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uv.app_plantae.pojo.Plantas;

import java.util.ArrayList;
import java.util.List;

public class DetalleMisPlantas extends AppCompatActivity {

    private ImageView imgItemDetail;
    private TextView tvTituloDetail, tvDescripcionDetail, tvdescripcion;
    private Plantas itemDetail;
    DatabaseReference ref;
    private Button eliminar;

    private String cientifico = "";
    private String img = "";
    private String nombre = "";

    //Creamos un objeto de autenticacion
    private FirebaseAuth mAuth;

    //Creamos un objeto de la base de datos
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mis_plantas);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initViews();
        initValues();



        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(DetalleMisPlantas.this);
                alerta.setMessage("¿Desea eliminar esta planta de Mis Plantas?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminarMiPlanta();
                                Toast.makeText(DetalleMisPlantas.this, "Planta eliminada exitosamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DetalleMisPlantas.this, Barra_menu.class));
                                finish();
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

    private void initViews(){
        imgItemDetail = findViewById(R.id.imgItemDetails);
        tvTituloDetail = findViewById(R.id.TituloDetails);
        tvDescripcionDetail = findViewById(R.id.DescripcionDetails);
        tvdescripcion = findViewById(R.id.verDescripcion);
        eliminar = findViewById(R.id.btn_eliminar);
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


    private void eliminarMiPlanta(){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).child("MisPlantas").child("-NVR4S9Jg8Kz6fug4PdJ").removeValue();
    }

    //Toast.makeText(DetalleMisPlantas.this, "Planta agregada", Toast.LENGTH_SHORT).show();
    //Intent intent = new Intent(DetalleMisPlantas.this, Barra_menu.class);
    //startActivity(intent);
    //finish();
}