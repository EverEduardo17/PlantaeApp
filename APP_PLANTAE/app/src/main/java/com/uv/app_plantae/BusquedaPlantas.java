package com.uv.app_plantae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.Adapter.AdapterPlanta;
import com.uv.app_plantae.pojo.Plantas;

import java.util.ArrayList;

public class BusquedaPlantas extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Plantas> list;
    RecyclerView rv;
    SearchView searchView;
    AdapterPlanta adapter;

    LinearLayoutManager lm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        ref = FirebaseDatabase.getInstance().getReference().child("Plantas");
        rv = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView1);
        lm = new LinearLayoutManager(this);

        //Conectando el RecyclerView con el LineraLayoutManager
        rv.setLayoutManager(lm);

        list = new ArrayList<>();
        adapter = new AdapterPlanta(list);

        rv.setAdapter(adapter);

        //Completar la lista con los valores de la base de datos
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        Plantas pt = snapshot1.getValue(Plantas.class);
                        list.add(pt);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Buscador
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            //Si el estado del buscador cambiea, se activa
            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });
    }

    private void buscar(String s) {
        ArrayList<Plantas>milista = new ArrayList<>();
        for (Plantas obj : list){
            if (obj.getNombre() != null && obj.getNombre().toLowerCase().contains(s.toLowerCase())){
                milista.add(obj);
            }
        }
        AdapterPlanta adapter = new AdapterPlanta(milista);
        rv.setAdapter(adapter);
    }
}