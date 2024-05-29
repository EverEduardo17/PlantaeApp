package com.uv.app_plantae;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.Adapter.AdapterSitios;
import com.uv.app_plantae.pojo.SitiosC;

import java.util.ArrayList;
import java.util.List;

public class Sitios extends AppCompatActivity {

    private Button btn_agregarsitio;
    DatabaseReference ref;
    List<SitiosC> list;
    RecyclerView rv;
    AdapterSitios adapter;
    LinearLayoutManager lm;
    private FirebaseAuth mAuth;

    public static SitiosC newInstance() {
        return new SitiosC();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitios2);

        btn_agregarsitio = findViewById(R.id.AgregarSitio);

        rv = findViewById(R.id.rvsitios);
        lm = new LinearLayoutManager(this);

        rv.setLayoutManager(lm);
        list = new ArrayList<>();
        adapter = new AdapterSitios(list);
        rv.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        //Obtiene el id del usuario actual
        String id = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Sitios");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String key = snapshot1.getKey();
                        SitiosC st = snapshot1.getValue(SitiosC.class);
                        st.setId(key); // Asignar el ID al objeto SitiosC
                        list.add(st);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btn_agregarsitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sitios.this, AgregarSitio.class);
                startActivity(intent);
            }
        });
    }
}
